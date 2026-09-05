package com.ahmetemresanli.backend.service.impl;

import com.ahmetemresanli.backend.entity.Education;
import com.ahmetemresanli.backend.entity.EducationVerification;
import com.ahmetemresanli.backend.enums.EducationVerificationType;
import com.ahmetemresanli.backend.enums.VerificationStatus;
import com.ahmetemresanli.backend.exception.BusinessException;
import com.ahmetemresanli.backend.exception.ResourceNotFoundException;
import com.ahmetemresanli.backend.repository.EducationRepository;
import com.ahmetemresanli.backend.repository.EducationVerificationRepository;
import com.ahmetemresanli.backend.service.IEducationVerificationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import com.ahmetemresanli.backend.security.SecureTokenGenerator;
import com.ahmetemresanli.backend.service.IMailService;
import com.ahmetemresanli.backend.service.INotificationService;
import com.ahmetemresanli.backend.enums.NotificationType;
import org.springframework.beans.factory.annotation.Value;
import com.ahmetemresanli.backend.service.IAuditLogService;
import com.ahmetemresanli.backend.security.AccessControlService;

@Service
public class EducationVerificationServiceImpl
        implements IEducationVerificationService {

    private static final long EMAIL_TOKEN_VALID_HOURS = 24;

    private final EducationVerificationRepository verificationRepository;
    private final EducationRepository educationRepository;
    private final IMailService mailService;
    private final INotificationService notificationService;
    private final String publicBaseUrl;
    private final IAuditLogService auditLogService;
    private final AccessControlService access;

    public EducationVerificationServiceImpl(
            EducationVerificationRepository verificationRepository,
            EducationRepository educationRepository,
            IMailService mailService,
            INotificationService notificationService,
            @Value("${app.public-base-url:http://localhost:8080}") String publicBaseUrl,
            IAuditLogService auditLogService,
            AccessControlService access
    ) {
        this.verificationRepository = verificationRepository;
        this.educationRepository = educationRepository;
        this.mailService = mailService;
        this.notificationService = notificationService;
        this.publicBaseUrl = publicBaseUrl;
        this.auditLogService = auditLogService;
        this.access = access;
    }

    @Override
    @Transactional
    public EducationVerification requestUniversityEmailVerification(
            Long educationId,
            String verificationEmail
    ) {

        Education education = getEducation(educationId);

        if (verificationEmail == null
                || verificationEmail.isBlank()) {

            throw new BusinessException(
                    "University email cannot be empty"
            );
        }

        String normalizedEmail =
                verificationEmail.trim().toLowerCase();

        if (!isAcademicEmail(normalizedEmail)) {
            throw new BusinessException(
                    "A valid university email address is required"
            );
        }

        checkPendingVerification(educationId);

        EducationVerification verification =
                new EducationVerification();

        verification.setEducation(education);
        verification.setVerificationType(
                EducationVerificationType.UNIVERSITY_EMAIL
        );
        verification.setVerificationEmail(
                normalizedEmail
        );
        verification.setStatus(
                VerificationStatus.PENDING
        );

        verification.setToken(
                SecureTokenGenerator.generate()
        );

        verification.setExpiresAt(
                LocalDateTime.now()
                        .plusHours(EMAIL_TOKEN_VALID_HOURS)
        );

        EducationVerification saved = verificationRepository.save(verification);
        mailService.send(saved.getVerificationEmail(), "CareerMatch education verification",
                "Verify education: " + publicBaseUrl + "/api/education-verifications/verify-email?token=" + saved.getToken());
        return saved;
    }

    @Override
    @Transactional
    public EducationVerification requestDocumentVerification(
            Long educationId,
            String documentUrl
    ) {

        Education education = getEducation(educationId);

        if (documentUrl == null
                || documentUrl.isBlank()) {

            throw new BusinessException(
                    "Verification document cannot be empty"
            );
        }

        checkPendingVerification(educationId);

        EducationVerification verification =
                new EducationVerification();

        verification.setEducation(education);
        verification.setVerificationType(
                EducationVerificationType.DOCUMENT
        );
        verification.setDocumentUrl(
                documentUrl.trim()
        );
        verification.setStatus(
                VerificationStatus.PENDING
        );

        return verificationRepository.save(verification);
    }

    @Override
    @Transactional
    public EducationVerification verifyUniversityEmail(
            String token
    ) {

        if (token == null || token.isBlank()) {
            throw new BusinessException(
                    "Verification token cannot be empty"
            );
        }

        EducationVerification verification =
                verificationRepository
                        .findByToken(token)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Verification token not found"
                                )
                        );

        if (verification.getVerificationType()
                != EducationVerificationType.UNIVERSITY_EMAIL) {

            throw new BusinessException(
                    "This verification is not an email verification"
            );
        }

        if (verification.getStatus()
                != VerificationStatus.PENDING) {

            throw new BusinessException(
                    "Verification is no longer pending"
            );
        }

        if (verification.getExpiresAt() == null
                || verification.getExpiresAt()
                .isBefore(LocalDateTime.now())) {

            verification.setStatus(
                    VerificationStatus.EXPIRED
            );

            verificationRepository.save(
                    verification
            );

            throw new BusinessException(
                    "Verification token has expired"
            );
        }

        verification.setStatus(
                VerificationStatus.VERIFIED
        );

        verification.setVerifiedAt(
                LocalDateTime.now()
        );

        EducationVerification saved = verificationRepository.save(verification);
        notifyCandidate(saved, "Education verified", "Your university email verification succeeded");
        return saved;
    }

    @Override
    @Transactional
    public EducationVerification approveDocument(
            Long verificationId
    ) {

        EducationVerification verification =
                getVerification(verificationId);

        validatePendingDocument(
                verification
        );

        verification.setStatus(
                VerificationStatus.VERIFIED
        );

        verification.setVerifiedAt(
                LocalDateTime.now()
        );

        verification.setRejectionReason(null);

        auditLogService.record(access.currentUserId(), "EDUCATION_VERIFICATION_APPROVED", "EducationVerification",
                verification.getId(), "educationId=" + verification.getEducation().getId());

        EducationVerification saved = verificationRepository.save(verification);
        notifyCandidate(saved, "Education verified", "Your education document was approved");
        return saved;
    }

    @Override
    @Transactional
    public EducationVerification rejectDocument(
            Long verificationId,
            String rejectionReason
    ) {

        EducationVerification verification =
                getVerification(verificationId);

        validatePendingDocument(
                verification
        );

        if (rejectionReason == null
                || rejectionReason.isBlank()) {

            throw new BusinessException(
                    "Rejection reason cannot be empty"
            );
        }

        verification.setStatus(
                VerificationStatus.REJECTED
        );

        verification.setRejectionReason(
                rejectionReason.trim()
        );

        auditLogService.record(access.currentUserId(), "EDUCATION_VERIFICATION_REJECTED", "EducationVerification",
                verification.getId(), "educationId=" + verification.getEducation().getId());

        EducationVerification saved = verificationRepository.save(verification);
        notifyCandidate(saved, "Education verification rejected", "Your education document was rejected");
        return saved;
    }

    @Override
    public EducationVerification getVerificationById(
            Long id
    ) {

        return getVerification(id);
    }

    @Override
    public List<EducationVerification>
    getVerificationsByEducationId(
            Long educationId
    ) {

        if (!educationRepository.existsById(educationId)) {
            throw new ResourceNotFoundException(
                    "Education not found"
            );
        }

        return verificationRepository
                .findByEducationIdOrderByCreatedAtDesc(
                        educationId
                );
    }

    private Education getEducation(
            Long educationId
    ) {

        return educationRepository
                .findById(educationId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Education not found"
                        )
                );
    }

    private EducationVerification getVerification(
            Long verificationId
    ) {

        return verificationRepository
                .findById(verificationId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Education verification not found"
                        )
                );
    }

    private void validatePendingDocument(EducationVerification verification) {

        if (verification.getVerificationType() != EducationVerificationType.DOCUMENT) {
            throw new BusinessException("This verification is not a document verification");
        }

        if (verification.getStatus() != VerificationStatus.PENDING) {

            throw new BusinessException(
                    "Verification is no longer pending"
            );
        }
    }

    private void checkPendingVerification(
            Long educationId
    ) {

        verificationRepository
                .findFirstByEducationIdAndStatusOrderByCreatedAtDesc(
                        educationId,
                        VerificationStatus.PENDING
                )
                .ifPresent(existingVerification -> {

                    /*
                     * Email doğrulaması PENDING fakat token süresi
                     * geçmişse eski kaydı EXPIRED yapıp yeni isteğe
                     * izin veriyoruz.
                     */
                    if (existingVerification.getVerificationType()
                            == EducationVerificationType.UNIVERSITY_EMAIL
                            && existingVerification.getExpiresAt() != null
                            && existingVerification.getExpiresAt()
                            .isBefore(LocalDateTime.now())) {

                        existingVerification.setStatus(
                                VerificationStatus.EXPIRED
                        );

                        verificationRepository.save(
                                existingVerification
                        );

                        return;
                    }

                    throw new BusinessException(
                            "A pending verification already exists for this education"
                    );
                });
    }

    private boolean isAcademicEmail(
            String email
    ) {

        int atIndex = email.lastIndexOf("@");

        if (atIndex < 0
                || atIndex == email.length() - 1) {

            return false;
        }

        String domain =
                email.substring(atIndex + 1);

        return domain.endsWith(".edu.tr")
                || domain.endsWith(".edu")
                || domain.contains(".ac.")
                || domain.endsWith(".ac");
    }

    private void notifyCandidate(EducationVerification verification, String title, String message) {
        notificationService.create(verification.getEducation().getCandidateProfile().getUser().getId(),
                NotificationType.VERIFICATION, title, message, "educationId=" + verification.getEducation().getId());
    }

    @Override
    public Optional<EducationVerification> getEffectiveVerification(
            Long educationId
    ) {

        if (!educationRepository.existsById(educationId)) {
            throw new ResourceNotFoundException(
                    "Education not found"
            );
        }

        Optional<EducationVerification> verified =
                verificationRepository
                        .findFirstByEducationIdAndStatusOrderByCreatedAtDesc(
                                educationId,
                                VerificationStatus.VERIFIED
                        );

        if (verified.isPresent()) {
            return verified;
        }

        return verificationRepository
                .findFirstByEducationIdOrderByCreatedAtDesc(
                        educationId
                );
    }
}
