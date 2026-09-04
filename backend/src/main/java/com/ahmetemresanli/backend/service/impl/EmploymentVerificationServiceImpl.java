package com.ahmetemresanli.backend.service.impl;

import com.ahmetemresanli.backend.entity.EmploymentVerification;
import com.ahmetemresanli.backend.entity.Experience;
import com.ahmetemresanli.backend.enums.EmploymentVerificationType;
import com.ahmetemresanli.backend.enums.VerificationStatus;
import com.ahmetemresanli.backend.exception.BusinessException;
import com.ahmetemresanli.backend.exception.ResourceNotFoundException;
import com.ahmetemresanli.backend.repository.EmploymentVerificationRepository;
import com.ahmetemresanli.backend.repository.ExperienceRepository;
import com.ahmetemresanli.backend.service.IEmploymentVerificationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class EmploymentVerificationServiceImpl
        implements IEmploymentVerificationService {

    private static final long EMAIL_TOKEN_VALID_HOURS = 24;

    private static final Set<String> FREE_EMAIL_DOMAINS =
            Set.of(
                    "gmail.com",
                    "hotmail.com",
                    "outlook.com",
                    "yahoo.com",
                    "icloud.com"
            );

    private final EmploymentVerificationRepository verificationRepository;
    private final ExperienceRepository experienceRepository;

    public EmploymentVerificationServiceImpl(
            EmploymentVerificationRepository verificationRepository,
            ExperienceRepository experienceRepository
    ) {
        this.verificationRepository = verificationRepository;
        this.experienceRepository = experienceRepository;
    }

    @Override
    @Transactional
    public EmploymentVerification requestWorkEmailVerification(
            Long experienceId,
            String verificationEmail
    ) {

        Experience experience =
                getExperience(experienceId);

        if (verificationEmail == null
                || verificationEmail.isBlank()) {

            throw new BusinessException(
                    "Work email cannot be empty"
            );
        }

        String normalizedEmail =
                verificationEmail
                        .trim()
                        .toLowerCase();

        if (!isCorporateEmail(normalizedEmail)) {

            throw new BusinessException(
                    "A corporate work email address is required"
            );
        }

        checkPendingVerification(experienceId);

        EmploymentVerification verification =
                new EmploymentVerification();

        verification.setExperience(experience);

        verification.setVerificationType(
                EmploymentVerificationType.WORK_EMAIL
        );

        verification.setVerificationEmail(
                normalizedEmail
        );

        verification.setStatus(
                VerificationStatus.PENDING
        );

        verification.setToken(
                UUID.randomUUID().toString()
        );

        verification.setExpiresAt(
                LocalDateTime.now()
                        .plusHours(EMAIL_TOKEN_VALID_HOURS)
        );

        return verificationRepository.save(
                verification
        );
    }

    @Override
    @Transactional
    public EmploymentVerification requestDocumentVerification(
            Long experienceId,
            String documentUrl
    ) {

        Experience experience =
                getExperience(experienceId);

        if (documentUrl == null
                || documentUrl.isBlank()) {

            throw new BusinessException(
                    "Verification document cannot be empty"
            );
        }

        checkPendingVerification(experienceId);

        EmploymentVerification verification =
                new EmploymentVerification();

        verification.setExperience(experience);

        verification.setVerificationType(
                EmploymentVerificationType.DOCUMENT
        );

        verification.setDocumentUrl(
                documentUrl.trim()
        );

        verification.setStatus(
                VerificationStatus.PENDING
        );

        return verificationRepository.save(
                verification
        );
    }

    @Override
    @Transactional
    public EmploymentVerification verifyWorkEmail(
            String token
    ) {

        if (token == null
                || token.isBlank()) {

            throw new BusinessException(
                    "Verification token cannot be empty"
            );
        }

        EmploymentVerification verification =
                verificationRepository
                        .findByToken(token)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Verification token not found"
                                )
                        );

        if (verification.getVerificationType()
                != EmploymentVerificationType.WORK_EMAIL) {

            throw new BusinessException(
                    "This verification is not a work email verification"
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

        return verificationRepository.save(
                verification
        );
    }

    @Override
    @Transactional
    public EmploymentVerification approveDocument(
            Long verificationId
    ) {

        EmploymentVerification verification =
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

        return verificationRepository.save(
                verification
        );
    }

    @Override
    @Transactional
    public EmploymentVerification rejectDocument(
            Long verificationId,
            String rejectionReason
    ) {

        EmploymentVerification verification =
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

        return verificationRepository.save(
                verification
        );
    }

    @Override
    public EmploymentVerification getVerificationById(
            Long id
    ) {

        return getVerification(id);
    }

    @Override
    public List<EmploymentVerification>
    getVerificationsByExperienceId(
            Long experienceId
    ) {

        if (!experienceRepository
                .existsById(experienceId)) {

            throw new ResourceNotFoundException(
                    "Experience not found"
            );
        }

        return verificationRepository
                .findByExperienceIdOrderByCreatedAtDesc(
                        experienceId
                );
    }

    @Override
    public Optional<EmploymentVerification> getEffectiveVerification(
            Long experienceId
    ) {

        if (!experienceRepository.existsById(experienceId)) {

            throw new ResourceNotFoundException(
                    "Experience not found"
            );
        }

        Optional<EmploymentVerification> verified =
                verificationRepository
                        .findFirstByExperienceIdAndStatusOrderByCreatedAtDesc(
                                experienceId,
                                VerificationStatus.VERIFIED
                        );

        if (verified.isPresent()) {
            return verified;
        }

        return verificationRepository
                .findFirstByExperienceIdOrderByCreatedAtDesc(
                        experienceId
                );
    }

    private Experience getExperience(
            Long experienceId
    ) {

        return experienceRepository
                .findById(experienceId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Experience not found"
                        )
                );
    }

    private EmploymentVerification getVerification(
            Long verificationId
    ) {

        return verificationRepository
                .findById(verificationId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Employment verification not found"
                        )
                );
    }

    private void validatePendingDocument(
            EmploymentVerification verification
    ) {

        if (verification.getVerificationType()
                != EmploymentVerificationType.DOCUMENT) {

            throw new BusinessException(
                    "This verification is not a document verification"
            );
        }

        if (verification.getStatus()
                != VerificationStatus.PENDING) {

            throw new BusinessException(
                    "Verification is no longer pending"
            );
        }
    }

    private void checkPendingVerification(
            Long experienceId
    ) {

        verificationRepository
                .findFirstByExperienceIdAndStatusOrderByCreatedAtDesc(
                        experienceId,
                        VerificationStatus.PENDING
                )
                .ifPresent(existingVerification -> {

                    if (existingVerification
                            .getVerificationType()
                            == EmploymentVerificationType.WORK_EMAIL

                            && existingVerification
                            .getExpiresAt() != null

                            && existingVerification
                            .getExpiresAt()
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
                            "A pending verification already exists for this experience"
                    );
                });
    }

    private boolean isCorporateEmail(
            String email
    ) {

        int atIndex =
                email.lastIndexOf("@");

        if (atIndex <= 0
                || atIndex == email.length() - 1) {

            return false;
        }

        String domain =
                email.substring(
                        atIndex + 1
                );

        return !FREE_EMAIL_DOMAINS.contains(
                domain
        );
    }
}