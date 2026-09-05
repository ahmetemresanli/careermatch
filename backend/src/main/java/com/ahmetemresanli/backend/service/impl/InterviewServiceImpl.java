package com.ahmetemresanli.backend.service.impl;

import com.ahmetemresanli.backend.entity.Application;
import com.ahmetemresanli.backend.entity.Interview;
import com.ahmetemresanli.backend.enums.ApplicationStatus;
import com.ahmetemresanli.backend.enums.InterviewMode;
import com.ahmetemresanli.backend.enums.InterviewStatus;
import com.ahmetemresanli.backend.enums.InterviewType;
import com.ahmetemresanli.backend.exception.BusinessException;
import com.ahmetemresanli.backend.exception.ResourceNotFoundException;
import com.ahmetemresanli.backend.repository.ApplicationRepository;
import com.ahmetemresanli.backend.repository.CandidateProfileRepository;
import com.ahmetemresanli.backend.repository.CompanyRepository;
import com.ahmetemresanli.backend.repository.InterviewRepository;
import com.ahmetemresanli.backend.service.IInterviewService;
import com.ahmetemresanli.backend.service.INotificationService;
import com.ahmetemresanli.backend.service.IMailService;
import com.ahmetemresanli.backend.service.IAuditLogService;
import com.ahmetemresanli.backend.enums.NotificationType;
import com.ahmetemresanli.backend.security.AccessControlService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class InterviewServiceImpl
        implements IInterviewService {

    private final InterviewRepository interviewRepository;
    private final ApplicationRepository applicationRepository;
    private final CandidateProfileRepository candidateProfileRepository;
    private final CompanyRepository companyRepository;
    private final INotificationService notificationService;
    private final IMailService mailService;
    private final IAuditLogService auditLogService;
    private final AccessControlService access;

    public InterviewServiceImpl(
            InterviewRepository interviewRepository,
            ApplicationRepository applicationRepository,
            CandidateProfileRepository candidateProfileRepository,
            CompanyRepository companyRepository,
            INotificationService notificationService,
            IMailService mailService,
            IAuditLogService auditLogService,
            AccessControlService access
    ) {
        this.interviewRepository = interviewRepository;
        this.applicationRepository = applicationRepository;
        this.candidateProfileRepository = candidateProfileRepository;
        this.companyRepository = companyRepository;
        this.notificationService = notificationService;
        this.mailService = mailService;
        this.auditLogService = auditLogService;
        this.access = access;
    }

    @Override
    @Transactional
    public Interview createInterview(
            Long applicationId,
            InterviewType interviewType,
            InterviewMode interviewMode,
            LocalDateTime scheduledAt,
            Integer durationMinutes,
            String meetingUrl,
            String location,
            String notes
    ) {

        Application application =
                applicationRepository
                        .findById(applicationId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Application not found"
                                )
                        );

        /*
         * Görüşme yalnızca SHORTLISTED veya
         * zaten INTERVIEW aşamasında bulunan
         * başvurular için oluşturulabilir.
         */
        if (application.getStatus()
                != ApplicationStatus.SHORTLISTED
                &&
                application.getStatus()
                        != ApplicationStatus.INTERVIEW) {

            throw new BusinessException(
                    "Interview can only be created for SHORTLISTED or INTERVIEW applications"
            );
        }

        if (interviewType == null) {
            throw new BusinessException(
                    "Interview type cannot be null"
            );
        }

        if (interviewMode == null) {
            throw new BusinessException(
                    "Interview mode cannot be null"
            );
        }

        if (scheduledAt == null) {
            throw new BusinessException(
                    "Interview date cannot be null"
            );
        }

        if (!scheduledAt.isAfter(LocalDateTime.now())) {
            throw new BusinessException(
                    "Interview must be scheduled for a future date"
            );
        }

        if (durationMinutes == null
                || durationMinutes <= 0) {

            throw new BusinessException(
                    "Interview duration must be greater than zero"
            );
        }

        validateInterviewLocation(
                interviewMode,
                meetingUrl,
                location
        );

        Interview interview = new Interview();

        interview.setApplication(application);

        interview.setInterviewType(
                interviewType
        );

        interview.setInterviewMode(
                interviewMode
        );

        interview.setStatus(
                InterviewStatus.SCHEDULED
        );

        interview.setScheduledAt(
                scheduledAt
        );

        interview.setDurationMinutes(
                durationMinutes
        );

        /*
         * ONLINE görüşmede meetingUrl kullanıyoruz.
         */
        if (interviewMode == InterviewMode.ONLINE) {

            interview.setMeetingUrl(
                    meetingUrl.trim()
            );

            interview.setLocation(null);
        }

        /*
         * ONSITE görüşmede fiziksel location kullanıyoruz.
         */
        else if (interviewMode == InterviewMode.ONSITE) {

            interview.setLocation(
                    location.trim()
            );

            interview.setMeetingUrl(null);
        }

        /*
         * PHONE için URL veya fiziksel konum
         * gerekmiyor.
         */
        else {

            interview.setMeetingUrl(null);
            interview.setLocation(null);
        }

        interview.setNotes(
                notes == null
                        ? null
                        : notes.trim()
        );

        /*
         * İlk görüşme oluşturulduğu anda
         * application:
         *
         * SHORTLISTED -> INTERVIEW
         */
        if (application.getStatus()
                == ApplicationStatus.SHORTLISTED) {

            application.setStatus(
                    ApplicationStatus.INTERVIEW
            );

            applicationRepository.save(
                    application
            );
        }

        Interview saved = interviewRepository.save(interview);
        Long candidateUserId = application.getCandidateProfile().getUser().getId();
        notificationService.create(candidateUserId, NotificationType.INTERVIEW, "Interview scheduled",
                application.getJobPosting().getTitle() + " interview: " + scheduledAt, "interviewId=" + saved.getId());
        mailService.send(application.getCandidateProfile().getUser().getEmail(), "CareerMatch interview scheduled",
                "Your interview for " + application.getJobPosting().getTitle() + " is scheduled at " + scheduledAt);
        auditLogService.record(access.currentUserId(), "INTERVIEW_CREATED", "Interview", saved.getId(),
                "applicationId=" + applicationId);
        return saved;
    }

    @Override
    @Transactional
    public Interview completeInterview(
            Long interviewId,
            String feedback
    ) {

        Interview interview =
                getInterviewById(interviewId);

        if (interview.getStatus()
                != InterviewStatus.SCHEDULED) {

            throw new BusinessException(
                    "Only a scheduled interview can be completed"
            );
        }

        interview.setStatus(
                InterviewStatus.COMPLETED
        );

        interview.setCompletedAt(
                LocalDateTime.now()
        );

        interview.setFeedback(
                feedback == null
                        ? null
                        : feedback.trim()
        );

        Interview saved = interviewRepository.save(interview);
        notificationService.create(saved.getApplication().getCandidateProfile().getUser().getId(), NotificationType.INTERVIEW,
                "Interview completed", "Your interview was marked completed", "interviewId=" + saved.getId());
        return saved;
    }

    @Override
    @Transactional
    public Interview cancelInterview(
            Long interviewId
    ) {

        Interview interview =
                getInterviewById(interviewId);

        if (interview.getStatus()
                != InterviewStatus.SCHEDULED) {

            throw new BusinessException(
                    "Only a scheduled interview can be cancelled"
            );
        }

        interview.setStatus(
                InterviewStatus.CANCELLED
        );

        Interview saved = interviewRepository.save(interview);
        notificationService.create(saved.getApplication().getCandidateProfile().getUser().getId(), NotificationType.INTERVIEW,
                "Interview cancelled", "Your interview was cancelled", "interviewId=" + saved.getId());
        return saved;
    }

    @Override
    public Interview getInterviewById(
            Long interviewId
    ) {

        return interviewRepository
                .findById(interviewId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Interview not found"
                        )
                );
    }

    @Override
    public List<Interview>
    getInterviewsByApplication(
            Long applicationId
    ) {

        if (!applicationRepository
                .existsById(applicationId)) {

            throw new ResourceNotFoundException(
                    "Application not found"
            );
        }

        return interviewRepository
                .findByApplicationIdOrderByScheduledAtAsc(
                        applicationId
                );
    }

    @Override
    public List<Interview>
    getInterviewsByCandidate(
            Long candidateProfileId
    ) {

        if (!candidateProfileRepository
                .existsById(candidateProfileId)) {

            throw new ResourceNotFoundException(
                    "Candidate profile not found"
            );
        }

        return interviewRepository
                .findByApplicationCandidateProfileIdOrderByScheduledAtDesc(
                        candidateProfileId
                );
    }

    @Override
    public List<Interview>
    getInterviewsByCompany(
            Long companyId
    ) {

        if (!companyRepository
                .existsById(companyId)) {

            throw new ResourceNotFoundException(
                    "Company not found"
            );
        }

        return interviewRepository
                .findByApplicationJobPostingCompanyIdOrderByScheduledAtDesc(
                        companyId
                );
    }

    private void validateInterviewLocation(
            InterviewMode interviewMode,
            String meetingUrl,
            String location
    ) {

        if (interviewMode == InterviewMode.ONLINE) {

            if (meetingUrl == null
                    || meetingUrl.isBlank()) {

                throw new BusinessException(
                        "Meeting URL is required for online interviews"
                );
            }
        }

        if (interviewMode == InterviewMode.ONSITE) {

            if (location == null
                    || location.isBlank()) {

                throw new BusinessException(
                        "Location is required for onsite interviews"
                );
            }
        }
    }
}
