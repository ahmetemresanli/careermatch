package com.ahmetemresanli.backend.service;

import com.ahmetemresanli.backend.entity.EducationVerification;

import java.util.List;
import java.util.Optional;

public interface IEducationVerificationService {

    EducationVerification requestUniversityEmailVerification(
            Long educationId,
            String verificationEmail
    );

    EducationVerification requestDocumentVerification(
            Long educationId,
            String documentUrl
    );

    EducationVerification verifyUniversityEmail(
            String token
    );

    EducationVerification approveDocument(
            Long verificationId
    );

    EducationVerification rejectDocument(
            Long verificationId,
            String rejectionReason
    );

    EducationVerification getVerificationById(
            Long id
    );

    List<EducationVerification> getVerificationsByEducationId(
            Long educationId
    );

    Optional<EducationVerification> getEffectiveVerification(
            Long educationId
    );
}