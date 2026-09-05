package com.ahmetemresanli.backend.service.impl;

import com.ahmetemresanli.backend.entity.Application;
import com.ahmetemresanli.backend.entity.CandidateProfile;
import com.ahmetemresanli.backend.entity.JobPosting;
import com.ahmetemresanli.backend.entity.Resume;
import com.ahmetemresanli.backend.enums.ApplicationStatus;
import com.ahmetemresanli.backend.enums.JobStatus;
import com.ahmetemresanli.backend.exception.BusinessException;
import com.ahmetemresanli.backend.exception.DuplicateResourceException;
import com.ahmetemresanli.backend.exception.ResourceNotFoundException;
import com.ahmetemresanli.backend.repository.ApplicationRepository;
import com.ahmetemresanli.backend.repository.CandidateProfileRepository;
import com.ahmetemresanli.backend.repository.JobPostingRepository;
import com.ahmetemresanli.backend.repository.ResumeRepository;
import com.ahmetemresanli.backend.service.IApplicationService;
import com.ahmetemresanli.backend.service.INotificationService;
import com.ahmetemresanli.backend.service.IAuditLogService;
import com.ahmetemresanli.backend.enums.NotificationType;
import com.ahmetemresanli.backend.security.AccessControlService;
import com.ahmetemresanli.backend.repository.CompanyMemberRepository;
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
    private final CompanyMemberRepository companyMemberRepository;
    private final INotificationService notificationService;
    private final IAuditLogService auditLogService;
    private final AccessControlService access;

    public ApplicationServiceImpl(
            ApplicationRepository applicationRepository,
            CandidateProfileRepository candidateProfileRepository,
            JobPostingRepository jobPostingRepository,
            ResumeRepository resumeRepository,
            CompanyMemberRepository companyMemberRepository,
            INotificationService notificationService,
            IAuditLogService auditLogService,
            AccessControlService access
    ) {
        this.applicationRepository = applicationRepository;
        this.candidateProfileRepository = candidateProfileRepository;
        this.jobPostingRepository = jobPostingRepository;
        this.resumeRepository = resumeRepository;
        this.companyMemberRepository = companyMemberRepository;
        this.notificationService = notificationService;
        this.auditLogService = auditLogService;
        this.access = access;
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

        /*
         * Sadece PUBLISHED ilanlara başvuru yapılabilir.
         */
        if (jobPosting.getStatus() != JobStatus.PUBLISHED) {

            throw new BusinessException(
                    "Applications can only be made to published job postings"
            );
        }

        /*
         * Son başvuru tarihi geçmişse başvuru yapılamaz.
         */
        if (jobPosting.getApplicationDeadline() != null
                && jobPosting.getApplicationDeadline()
                .isBefore(LocalDateTime.now())) {

            throw new BusinessException(
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

            throw new DuplicateResourceException(
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
                            new ResourceNotFoundException(
                                    "Candidate does not have a default resume"
                            )
                    );

        } else {

            resume = resumeRepository
                    .findById(resumeId)
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
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

            throw new BusinessException(
                    "Resume does not belong to this candidate"
            );
        }

        if (!resume.isActive()) {

            throw new BusinessException(
                    "Inactive resume cannot be used"
            );
        }

        Application application = new Application();

        application.setCandidateProfile(candidateProfile);
        application.setJobPosting(jobPosting);
        application.setResume(resume);
        application.setStatus(ApplicationStatus.APPLIED);

        if (coverLetter != null
                && !coverLetter.isBlank()) {

            application.setCoverLetter(
                    coverLetter.trim()
            );
        }

        Application saved = applicationRepository.save(application);
        companyMemberRepository.findByCompanyId(jobPosting.getCompany().getId()).stream()
                .filter(member -> member.isActive() && member.getUser().isActive())
                .forEach(member -> notificationService.create(member.getUser().getId(), NotificationType.APPLICATION_STATUS,
                        "New application", candidateProfile.getFirstName() + " applied to " + jobPosting.getTitle(),
                        "applicationId=" + saved.getId()));
        return saved;
    }

    @Override
    public Application getApplicationById(Long id) {

        return applicationRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
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

            throw new ResourceNotFoundException(
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

            throw new ResourceNotFoundException(
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

            throw new ResourceNotFoundException(
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
                                new ResourceNotFoundException(
                                        "Application not found"
                                )
                        );

        if (newStatus == null) {

            throw new BusinessException(
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

            throw new BusinessException(
                    "Invalid application status transition: "
                            + currentStatus
                            + " -> "
                            + newStatus
            );
        }

        application.setStatus(newStatus);

        Application saved = applicationRepository.save(application);
        notificationService.create(saved.getCandidateProfile().getUser().getId(), NotificationType.APPLICATION_STATUS,
                "Application status updated", saved.getJobPosting().getTitle() + ": " + newStatus,
                "applicationId=" + saved.getId());
        auditLogService.record(access.currentUserId(), "APPLICATION_STATUS_CHANGED", "Application", saved.getId(),
                currentStatus + " -> " + newStatus);
        return saved;
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
