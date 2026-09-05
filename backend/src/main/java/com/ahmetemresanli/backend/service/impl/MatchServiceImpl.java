package com.ahmetemresanli.backend.service.impl;

import com.ahmetemresanli.backend.entity.CandidateProfile;
import com.ahmetemresanli.backend.entity.JobPosting;
import com.ahmetemresanli.backend.entity.Match;
import com.ahmetemresanli.backend.enums.JobSearchStatus;
import com.ahmetemresanli.backend.enums.JobStatus;
import com.ahmetemresanli.backend.enums.MatchStatus;
import com.ahmetemresanli.backend.exception.BusinessException;
import com.ahmetemresanli.backend.exception.ResourceNotFoundException;
import com.ahmetemresanli.backend.repository.CandidateProfileRepository;
import com.ahmetemresanli.backend.repository.JobPostingRepository;
import com.ahmetemresanli.backend.repository.MatchRepository;
import com.ahmetemresanli.backend.service.IMatchService;
import com.ahmetemresanli.backend.service.INotificationService;
import com.ahmetemresanli.backend.enums.NotificationType;
import com.ahmetemresanli.backend.service.matching.MatchScoreCalculator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

@Service
public class MatchServiceImpl implements IMatchService {

    private static final BigDecimal MIN_RECOMMENDATION_SCORE =
            BigDecimal.valueOf(60);

    private final MatchRepository matchRepository;
    private final CandidateProfileRepository candidateProfileRepository;
    private final JobPostingRepository jobPostingRepository;
    private final MatchScoreCalculator matchScoreCalculator;
    private final INotificationService notificationService;

    public MatchServiceImpl(
            MatchRepository matchRepository,
            CandidateProfileRepository candidateProfileRepository,
            JobPostingRepository jobPostingRepository,
            MatchScoreCalculator matchScoreCalculator,
            INotificationService notificationService
    ) {
        this.matchRepository = matchRepository;
        this.candidateProfileRepository = candidateProfileRepository;
        this.jobPostingRepository = jobPostingRepository;
        this.matchScoreCalculator = matchScoreCalculator;
        this.notificationService = notificationService;
    }

    @Override
    @Transactional
    public Match calculateAndSaveMatch(
            Long candidateProfileId,
            Long jobPostingId
    ) {

        CandidateProfile candidateProfile =
                candidateProfileRepository
                        .findById(candidateProfileId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Candidate profile not found"
                                )
                        );

        JobPosting jobPosting =
                jobPostingRepository
                        .findById(jobPostingId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Job posting not found"
                                )
                        );

        if (jobPosting.getStatus() != JobStatus.PUBLISHED) {
            throw new BusinessException(
                    "Match can only be calculated for published job postings"
            );
        }

        BigDecimal score =
                matchScoreCalculator.calculateScore(
                        candidateProfile,
                        jobPosting
                );

        var existingMatch = matchRepository.findByCandidateProfileIdAndJobPostingId(
                                candidateProfileId,
                                jobPostingId
                        );
        Match match = existingMatch
                        .orElseGet(Match::new);

        match.setCandidateProfile(candidateProfile);
        match.setJobPosting(jobPosting);
        match.setScore(score);

        Match saved = matchRepository.save(match);
        if (existingMatch.isEmpty() && score.compareTo(MIN_RECOMMENDATION_SCORE) >= 0) {
            notificationService.create(candidateProfile.getUser().getId(), NotificationType.JOB_RECOMMENDATION,
                    "New job recommendation", jobPosting.getTitle() + " matches your profile",
                    "jobPostingId=" + jobPostingId + ",score=" + score);
        }
        return saved;
    }

    @Override
    public Match getMatchById(Long id) {

        return matchRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Match not found"
                        )
                );
    }

    @Override
    public List<Match> getMatchesByCandidateProfileId(
            Long candidateProfileId
    ) {

        if (!candidateProfileRepository
                .existsById(candidateProfileId)) {

            throw new ResourceNotFoundException(
                    "Candidate profile not found"
            );
        }

        return matchRepository
                .findByCandidateProfileId(
                        candidateProfileId
                ).stream()
                .filter(match -> match.getJobPosting().getStatus() == JobStatus.PUBLISHED)
                .toList();
    }

    @Override
    public List<Match> getMatchesByJobPostingId(
            Long jobPostingId
    ) {

        if (!jobPostingRepository
                .existsById(jobPostingId)) {

            throw new ResourceNotFoundException(
                    "Job posting not found"
            );
        }

        return matchRepository
                .findByJobPostingId(
                        jobPostingId
                ).stream()
                .filter(match -> match.getCandidateProfile().isVisibleToRecruiters())
                .filter(match -> match.getCandidateProfile().getJobSearchStatus() != JobSearchStatus.NOT_LOOKING)
                .toList();
    }

    @Override
    @Transactional
    public List<Match> calculateMatchesForCandidate(
            Long candidateProfileId
    ) {

        if (!candidateProfileRepository
                .existsById(candidateProfileId)) {

            throw new ResourceNotFoundException(
                    "Candidate profile not found"
            );
        }

        List<JobPosting> publishedJobs =
                jobPostingRepository.findByStatus(
                        JobStatus.PUBLISHED
                );

        return publishedJobs.stream()
                .map(jobPosting ->
                        calculateAndSaveMatch(
                                candidateProfileId,
                                jobPosting.getId()
                        )
                )
                .filter(match ->
                        match.getStatus()
                                == MatchStatus.ACTIVE
                )
                .filter(match ->
                        match.getScore().compareTo(
                                MIN_RECOMMENDATION_SCORE
                        ) >= 0
                )
                .sorted(
                        Comparator.comparing(
                                Match::getScore
                        ).reversed()
                )
                .toList();
    }

    @Override
    @Transactional
    public List<Match> calculateMatchesForJobPosting(
            Long jobPostingId
    ) {

        JobPosting jobPosting =
                jobPostingRepository.findById(jobPostingId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Job posting not found"
                                )
                        );

        if (jobPosting.getStatus() != JobStatus.PUBLISHED) {
            throw new BusinessException(
                    "Candidate recommendations can only be calculated for published job postings"
            );
        }

        List<CandidateProfile> candidates =
                candidateProfileRepository.findAll();

        return candidates.stream()

                .filter(CandidateProfile::isVisibleToRecruiters)

                .filter(candidate ->
                        candidate.getJobSearchStatus()
                                != JobSearchStatus.NOT_LOOKING
                )

                .map(candidate ->
                        calculateAndSaveMatch(
                                candidate.getId(),
                                jobPostingId
                        )
                )

                .filter(match ->
                        match.getStatus()
                                == MatchStatus.ACTIVE
                )

                .filter(match ->
                        match.getScore().compareTo(
                                MIN_RECOMMENDATION_SCORE
                        ) >= 0
                )

                .sorted(
                        Comparator.comparing(
                                Match::getScore
                        ).reversed()
                )

                .toList();
    }

    @Override
    public List<Match> getCandidateRecommendations(
            Long candidateProfileId
    ) {

        if (!candidateProfileRepository.existsById(candidateProfileId)) {
            throw new ResourceNotFoundException(
                    "Candidate profile not found"
            );
        }

        return matchRepository
                .findByCandidateProfileIdAndStatusAndScoreGreaterThanEqualOrderByScoreDesc(
                        candidateProfileId,
                        MatchStatus.ACTIVE,
                        MIN_RECOMMENDATION_SCORE
                );
    }

    @Override
    public List<Match> getJobPostingRecommendations(
            Long jobPostingId
    ) {

        if (!jobPostingRepository.existsById(jobPostingId)) {
            throw new ResourceNotFoundException(
                    "Job posting not found"
            );
        }

        return matchRepository
                .findByJobPostingIdAndStatusAndScoreGreaterThanEqualOrderByScoreDesc(
                        jobPostingId,
                        MatchStatus.ACTIVE,
                        MIN_RECOMMENDATION_SCORE
                );
    }
}
