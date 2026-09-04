package com.ahmetemresanli.backend.mapper;

import com.ahmetemresanli.backend.dto.request.ExperienceCreateRequest;
import com.ahmetemresanli.backend.dto.response.ExperienceResponse;
import com.ahmetemresanli.backend.entity.EmploymentVerification;
import com.ahmetemresanli.backend.entity.Experience;
import com.ahmetemresanli.backend.enums.VerificationStatus;

public final class ExperienceMapper {

    private ExperienceMapper() {
    }

    public static Experience toEntity(
            ExperienceCreateRequest request
    ) {

        Experience experience = new Experience();

        experience.setCompanyName(
                request.getCompanyName()
        );

        experience.setPositionTitle(
                request.getPositionTitle()
        );

        experience.setStartDate(
                request.getStartDate()
        );

        experience.setEndDate(
                request.getEndDate()
        );

        experience.setCurrentlyWorking(
                request.getCurrentlyWorking()
        );

        experience.setDescription(
                request.getDescription()
        );

        return experience;
    }

    public static ExperienceResponse toResponse(
            Experience experience
    ) {

        ExperienceResponse response =
                new ExperienceResponse();

        response.setId(
                experience.getId()
        );

        if (experience.getCandidateProfile() != null) {

            response.setCandidateProfileId(
                    experience
                            .getCandidateProfile()
                            .getId()
            );
        }

        response.setCompanyName(
                experience.getCompanyName()
        );

        response.setPositionTitle(
                experience.getPositionTitle()
        );

        response.setStartDate(
                experience.getStartDate()
        );

        response.setEndDate(
                experience.getEndDate()
        );

        response.setCurrentlyWorking(
                experience.isCurrentlyWorking()
        );

        response.setDescription(
                experience.getDescription()
        );

        response.setCreatedAt(
                experience.getCreatedAt()
        );

        response.setUpdatedAt(
                experience.getUpdatedAt()
        );

        return response;
    }

    public static ExperienceResponse toResponse(
            Experience experience,
            EmploymentVerification verification
    ) {

        ExperienceResponse response =
                toResponse(experience);

        if (verification == null) {

            response.setVerified(false);
            response.setVerificationStatus(null);
            response.setVerificationType(null);
            response.setVerifiedAt(null);

            return response;
        }

        response.setVerificationStatus(
                verification.getStatus()
        );

        response.setVerificationType(
                verification.getVerificationType()
        );

        response.setVerified(
                verification.getStatus()
                        == VerificationStatus.VERIFIED
        );

        response.setVerifiedAt(
                verification.getVerifiedAt()
        );

        return response;
    }
}