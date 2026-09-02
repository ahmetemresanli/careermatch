package com.ahmetemresanli.backend.mapper;

import com.ahmetemresanli.backend.dto.request.CandidateProfileCreateRequest;
import com.ahmetemresanli.backend.dto.response.CandidateProfileResponse;
import com.ahmetemresanli.backend.entity.CandidateProfile;

public final class CandidateProfileMapper {

    private CandidateProfileMapper() {
    }

    public static CandidateProfile toEntity(
            CandidateProfileCreateRequest request
    ) {

        if (request == null) {
            return null;
        }

        CandidateProfile candidateProfile =
                new CandidateProfile();

        candidateProfile.setFirstName(request.getFirstName());
        candidateProfile.setLastName(request.getLastName());
        candidateProfile.setAbout(request.getAbout());
        candidateProfile.setCity(request.getCity());
        candidateProfile.setCountry(request.getCountry());
        candidateProfile.setGithubUrl(request.getGithubUrl());
        candidateProfile.setLinkedinUrl(request.getLinkedinUrl());
        candidateProfile.setWebsiteUrl(request.getWebsiteUrl());

        if (request.getJobSearchStatus() != null) {
            candidateProfile.setJobSearchStatus(
                    request.getJobSearchStatus()
            );
        }

        if (request.getVisibleToRecruiters() != null) {
            candidateProfile.setVisibleToRecruiters(
                    request.getVisibleToRecruiters()
            );
        }

        candidateProfile.setExpectedMinSalary(
                request.getExpectedMinSalary()
        );

        candidateProfile.setExpectedMaxSalary(
                request.getExpectedMaxSalary()
        );

        candidateProfile.setPreferredWorkModel(
                request.getPreferredWorkModel()
        );

        return candidateProfile;
    }

    public static CandidateProfileResponse toResponse(
            CandidateProfile candidateProfile
    ) {

        if (candidateProfile == null) {
            return null;
        }

        CandidateProfileResponse response =
                new CandidateProfileResponse();

        response.setId(candidateProfile.getId());

        if (candidateProfile.getUser() != null) {
            response.setUserId(
                    candidateProfile.getUser().getId()
            );
        }

        response.setFirstName(candidateProfile.getFirstName());
        response.setLastName(candidateProfile.getLastName());
        response.setAbout(candidateProfile.getAbout());
        response.setCity(candidateProfile.getCity());
        response.setCountry(candidateProfile.getCountry());
        response.setGithubUrl(candidateProfile.getGithubUrl());
        response.setLinkedinUrl(candidateProfile.getLinkedinUrl());
        response.setWebsiteUrl(candidateProfile.getWebsiteUrl());

        response.setJobSearchStatus(
                candidateProfile.getJobSearchStatus()
        );

        response.setVisibleToRecruiters(
                candidateProfile.isVisibleToRecruiters()
        );

        response.setExpectedMinSalary(
                candidateProfile.getExpectedMinSalary()
        );

        response.setExpectedMaxSalary(
                candidateProfile.getExpectedMaxSalary()
        );

        response.setPreferredWorkModel(
                candidateProfile.getPreferredWorkModel()
        );

        response.setCreatedAt(candidateProfile.getCreatedAt());
        response.setUpdatedAt(candidateProfile.getUpdatedAt());

        return response;
    }
}