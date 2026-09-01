package com.ahmetemresanli.backend.service.impl;

import com.ahmetemresanli.backend.entity.Application;
import com.ahmetemresanli.backend.entity.CandidateProfile;
import com.ahmetemresanli.backend.entity.JobPosting;
import com.ahmetemresanli.backend.entity.Resume;
import com.ahmetemresanli.backend.enums.ApplicationStatus;
import com.ahmetemresanli.backend.enums.JobStatus;
import com.ahmetemresanli.backend.repository.ApplicationRepository;
import com.ahmetemresanli.backend.repository.CandidateProfileRepository;
import com.ahmetemresanli.backend.repository.JobPostingRepository;
import com.ahmetemresanli.backend.repository.ResumeRepository;
import com.ahmetemresanli.backend.service.IApplicationService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ApplicationServiceImpl
        implements IApplicationService {

    private final ApplicationRepository applicationRepository;
    private final CandidateProfileRepository candidateProfileRepository;
    private final JobPostingRepository jobPostingRepository;
    private final ResumeRepository resumeRepository;

    public ApplicationServiceImpl(
            ApplicationRepository applicationRepository,
            CandidateProfileRepository candidateProfileRepository,
            JobPostingRepository jobPostingRepository,
            ResumeRepository resumeRepository
    ) {
        this.applicationRepository = applicationRepository;
        this.candidateProfileRepository = candidateProfileRepository;
        this.jobPostingRepository = jobPostingRepository;
        this.resumeRepository = resumeRepository;
    }

    @Override
    public Application applyToJob(
            Long candidateProfileId,
            Long jobPostingId,
            Long resumeId,
            String coverLetter
    ) {

        CandidateProfile candidateProfile =
                candidateProfileRepository
                        .findById(candidateProfileId)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Candidate profile not found"
                                )
                        );

        JobPosting jobPosting =
                jobPostingRepository
                        .findById(jobPostingId)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Job posting not found"
                                )
                        );

        /*
         * Sadece PUBLISHED ilanlara başvuru yapılabilir.
         */
        if (jobPosting.getStatus() != JobStatus.PUBLISHED) {

            throw new IllegalArgumentException(
                    "Applications can only be made to published job postings"
            );
        }

        /*
         * Son başvuru tarihi geçmişse başvuru yapılamaz.
         */
        if (jobPosting.getApplicationDeadline() != null &&
                jobPosting.getApplicationDeadline()
                        .isBefore(LocalDateTime.now())) {

            throw new IllegalArgumentException(
                    "Application deadline has passed"
            );
        }

        /*
         * Aynı aday aynı ilana ikinci kez başvuramaz.
         */
        if (applicationRepository
                .existsByCandidateProfileIdAndJobPostingId(
                        candidateProfileId,
                        jobPostingId
                )) {

            throw new IllegalArgumentException(
                    "Candidate has already applied to this job posting"
            );
        }

        Resume resume;

        /*
         * resumeId verilmediyse default CV kullan.
         */
        if (resumeId == null) {

            resume = resumeRepository
                    .findByCandidateProfileIdAndDefaultResumeTrue(
                            candidateProfileId
                    )
                    .orElseThrow(() ->
                            new IllegalArgumentException(
                                    "Candidate does not have a default resume"
                            )
                    );

        } else {

            resume = resumeRepository
                    .findById(resumeId)
                    .orElseThrow(() ->
                            new IllegalArgumentException(
                                    "Resume not found"
                            )
                    );
        }

        /*
         * Başka adayın CV'si kullanılamaz.
         */
        if (!resume.getCandidateProfile()
                .getId()
                .equals(candidateProfileId)) {

            throw new IllegalArgumentException(
                    "Resume does not belong to this candidate"
            );
        }

        if (!resume.isActive()) {

            throw new IllegalArgumentException(
                    "Inactive resume cannot be used"
            );
        }

        Application application = new Application();

        application.setCandidateProfile(candidateProfile);
        application.setJobPosting(jobPosting);
        application.setResume(resume);
        application.setStatus(ApplicationStatus.APPLIED);

        if (coverLetter != null &&
                !coverLetter.isBlank()) {

            application.setCoverLetter(
                    coverLetter.trim()
            );
        }

        return applicationRepository.save(application);
    }

    @Override
    public Application getApplicationById(Long id) {

        return applicationRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Application not found"
                        )
                );
    }

    @Override
    public List<Application>
    getApplicationsByCandidateProfileId(
            Long candidateProfileId
    ) {

        if (!candidateProfileRepository
                .existsById(candidateProfileId)) {

            throw new IllegalArgumentException(
                    "Candidate profile not found"
            );
        }

        return applicationRepository
                .findByCandidateProfileId(candidateProfileId);
    }

    @Override
    public List<Application>
    getApplicationsByJobPostingId(
            Long jobPostingId
    ) {

        if (!jobPostingRepository
                .existsById(jobPostingId)) {

            throw new IllegalArgumentException(
                    "Job posting not found"
            );
        }

        return applicationRepository
                .findByJobPostingId(jobPostingId);
    }

    @Override
    public List<Application>
    getApplicationsByJobPostingIdAndStatus(
            Long jobPostingId,
            ApplicationStatus status
    ) {

        if (!jobPostingRepository
                .existsById(jobPostingId)) {

            throw new IllegalArgumentException(
                    "Job posting not found"
            );
        }

        return applicationRepository
                .findByJobPostingIdAndStatus(
                        jobPostingId,
                        status
                );
    }

    @Override
    public Application updateApplicationStatus(
            Long applicationId,
            ApplicationStatus newStatus
    ) {

        Application application =
                applicationRepository
                        .findById(applicationId)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Application not found"
                                )
                        );

        if (newStatus == null) {

            throw new IllegalArgumentException(
                    "Application status cannot be null"
            );
        }

        ApplicationStatus currentStatus =
                application.getStatus();

        if (currentStatus == newStatus) {
            return application;
        }

        if (!isValidStatusTransition(
                currentStatus,
                newStatus
        )) {

            throw new IllegalArgumentException(
                    "Invalid application status transition: "
                            + currentStatus
                            + " -> "
                            + newStatus
            );
        }

        application.setStatus(newStatus);

        return applicationRepository.save(application);
    }

    private boolean isValidStatusTransition(
            ApplicationStatus currentStatus,
            ApplicationStatus newStatus
    ) {

        return switch (currentStatus) {

            case APPLIED ->
                    newStatus == ApplicationStatus.UNDER_REVIEW
                            ||
                            newStatus == ApplicationStatus.REJECTED;

            case UNDER_REVIEW ->
                    newStatus == ApplicationStatus.SHORTLISTED
                            ||
                            newStatus == ApplicationStatus.REJECTED;

            case SHORTLISTED ->
                    newStatus == ApplicationStatus.INTERVIEW
                            ||
                            newStatus == ApplicationStatus.REJECTED;

            case INTERVIEW ->
                    newStatus == ApplicationStatus.ACCEPTED
                            ||
                            newStatus == ApplicationStatus.REJECTED;

            case ACCEPTED, REJECTED -> false;
        };
    }
}