package com.ahmetemresanli.backend.mapper;

import com.ahmetemresanli.backend.dto.request.EducationCreateRequest;
import com.ahmetemresanli.backend.dto.response.EducationResponse;
import com.ahmetemresanli.backend.entity.Education;
import com.ahmetemresanli.backend.entity.EducationVerification;
import com.ahmetemresanli.backend.enums.VerificationStatus;

public final class EducationMapper {

    private EducationMapper() {
    }

    public static Education toEntity(
            EducationCreateRequest request
    ) {

        Education education = new Education();

        education.setUniversityName(
                request.getUniversityName()
        );

        education.setDepartment(
                request.getDepartment()
        );

        education.setEducationLevel(
                request.getEducationLevel()
        );

        education.setStartDate(
                request.getStartDate()
        );

        education.setEndDate(
                request.getEndDate()
        );

        education.setCurrentlyStudying(
                request.getCurrentlyStudying()
        );

        education.setDescription(
                request.getDescription()
        );

        return education;
    }

    /*
     * Verification bilgisi gerekmediğinde
     * normal Education response'u oluşturur.
     */
    public static EducationResponse toResponse(
            Education education
    ) {

        EducationResponse response =
                new EducationResponse();

        response.setId(
                education.getId()
        );

        if (education.getCandidateProfile() != null) {

            response.setCandidateProfileId(
                    education
                            .getCandidateProfile()
                            .getId()
            );
        }

        response.setUniversityName(
                education.getUniversityName()
        );

        response.setDepartment(
                education.getDepartment()
        );

        response.setEducationLevel(
                education.getEducationLevel()
        );

        response.setStartDate(
                education.getStartDate()
        );

        response.setEndDate(
                education.getEndDate()
        );

        response.setCurrentlyStudying(
                education.isCurrentlyStudying()
        );

        response.setDescription(
                education.getDescription()
        );

        response.setCreatedAt(
                education.getCreatedAt()
        );

        response.setUpdatedAt(
                education.getUpdatedAt()
        );

        return response;
    }

    /*
     * Education + Verification bilgilerini
     * birlikte response'a dönüştürür.
     */
    public static EducationResponse toResponse(
            Education education,
            EducationVerification verification
    ) {

        EducationResponse response =
                toResponse(education);

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