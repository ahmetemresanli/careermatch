package com.ahmetemresanli.backend.service.impl;

import com.ahmetemresanli.backend.entity.CandidateProfile;
import com.ahmetemresanli.backend.entity.JobPosting;
import com.ahmetemresanli.backend.entity.Match;
import com.ahmetemresanli.backend.enums.JobStatus;
import com.ahmetemresanli.backend.enums.MatchStatus;
import com.ahmetemresanli.backend.exception.BusinessException;
import com.ahmetemresanli.backend.exception.ResourceNotFoundException;
import com.ahmetemresanli.backend.repository.CandidateProfileRepository;
import com.ahmetemresanli.backend.repository.JobPostingRepository;
import com.ahmetemresanli.backend.repository.MatchRepository;
import com.ahmetemresanli.backend.service.IMatchService;
import com.ahmetemresanli.backend.service.matching.MatchScoreCalculator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

@Service
public class MatchServiceImpl implements IMatchService {

    private final MatchRepository matchRepository;
    private final CandidateProfileRepository candidateProfileRepository;
    private final JobPostingRepository jobPostingRepository;
    private final MatchScoreCalculator matchScoreCalculator;

    public MatchServiceImpl(
            MatchRepository matchRepository,
            CandidateProfileRepository candidateProfileRepository,
            JobPostingRepository jobPostingRepository,
            MatchScoreCalculator matchScoreCalculator
    ) {
        this.matchRepository = matchRepository;
        this.candidateProfileRepository = candidateProfileRepository;
        this.jobPostingRepository = jobPostingRepository;
        this.matchScoreCalculator = matchScoreCalculator;
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

        Match match =
                matchRepository
                        .findByCandidateProfileIdAndJobPostingId(
                                candidateProfileId,
                                jobPostingId
                        )
                        .orElseGet(Match::new);

        /*
         * Yeni Match ise candidate ve job bağlantısını kur.
         * Eski Match ise bunlar zaten mevcut ama tekrar set etmek
         * herhangi bir problem oluşturmaz.
         */
        match.setCandidateProfile(candidateProfile);
        match.setJobPosting(jobPosting);
        match.setScore(score);

        return matchRepository.save(match);
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
                );
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
                );
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
                .sorted(
                        Comparator.comparing(
                                Match::getScore
                        ).reversed()
                )
                .toList();
    }
}