package com.ahmetemresanli.backend.service;

import com.ahmetemresanli.backend.entity.EmploymentVerification;

import java.util.List;
import java.util.Optional;

public interface IEmploymentVerificationService {

    EmploymentVerification requestWorkEmailVerification(
            Long experienceId,
            String verificationEmail
    );

    EmploymentVerification requestDocumentVerification(
            Long experienceId,
            String documentUrl
    );

    EmploymentVerification verifyWorkEmail(
            String token
    );

    EmploymentVerification approveDocument(
            Long verificationId
    );

    EmploymentVerification rejectDocument(
            Long verificationId,
            String rejectionReason
    );

    EmploymentVerification getVerificationById(
            Long id
    );

    List<EmploymentVerification> getVerificationsByExperienceId(
            Long experienceId
    );

    Optional<EmploymentVerification> getEffectiveVerification(
            Long experienceId
    );
}