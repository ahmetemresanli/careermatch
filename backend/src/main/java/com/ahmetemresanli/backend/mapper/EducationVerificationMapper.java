package com.ahmetemresanli.backend.mapper;

import com.ahmetemresanli.backend.dto.response.EducationVerificationResponse;
import com.ahmetemresanli.backend.entity.EducationVerification;

public final class EducationVerificationMapper {

    private EducationVerificationMapper() {
    }

    public static EducationVerificationResponse toResponse(
            EducationVerification verification
    ) {

        EducationVerificationResponse response =
                new EducationVerificationResponse();

        response.setId(verification.getId());

        if (verification.getEducation() != null) {

            response.setEducationId(
                    verification.getEducation().getId()
            );

            if (verification.getEducation()
                    .getCandidateProfile() != null) {

                response.setCandidateProfileId(
                        verification.getEducation()
                                .getCandidateProfile()
                                .getId()
                );
            }
        }

        response.setVerificationType(
                verification.getVerificationType()
        );

        response.setStatus(
                verification.getStatus()
        );

        response.setVerificationEmail(
                verification.getVerificationEmail()
        );

        response.setDocumentUrl(
                verification.getDocumentUrl()
        );

        response.setToken(
                verification.getToken()
        );

        response.setExpiresAt(
                verification.getExpiresAt()
        );

        response.setVerifiedAt(
                verification.getVerifiedAt()
        );

        response.setRejectionReason(
                verification.getRejectionReason()
        );

        response.setCreatedAt(
                verification.getCreatedAt()
        );

        response.setUpdatedAt(
                verification.getUpdatedAt()
        );

        return response;
    }
}