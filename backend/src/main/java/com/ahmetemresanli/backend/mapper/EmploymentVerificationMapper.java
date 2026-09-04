package com.ahmetemresanli.backend.mapper;

import com.ahmetemresanli.backend.dto.response.EmploymentVerificationResponse;
import com.ahmetemresanli.backend.entity.EmploymentVerification;

public final class EmploymentVerificationMapper {

    private EmploymentVerificationMapper() {
    }

    public static EmploymentVerificationResponse toResponse(
            EmploymentVerification verification
    ) {

        EmploymentVerificationResponse response =
                new EmploymentVerificationResponse();

        response.setId(
                verification.getId()
        );

        if (verification.getExperience() != null) {

            response.setExperienceId(
                    verification.getExperience().getId()
            );

            if (verification.getExperience()
                    .getCandidateProfile() != null) {

                response.setCandidateProfileId(
                        verification.getExperience()
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