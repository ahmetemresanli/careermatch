package com.ahmetemresanli.backend.mapper;

import com.ahmetemresanli.backend.dto.response.MatchResponse;
import com.ahmetemresanli.backend.entity.Match;

public final class MatchMapper {

    private MatchMapper() {
    }

    public static MatchResponse toResponse(Match match) {

        MatchResponse response = new MatchResponse();

        response.setId(match.getId());

        if (match.getCandidateProfile() != null) {

            response.setCandidateProfileId(
                    match.getCandidateProfile().getId()
            );

            response.setCandidateFirstName(
                    match.getCandidateProfile().getFirstName()
            );

            response.setCandidateLastName(
                    match.getCandidateProfile().getLastName()
            );
        }

        if (match.getJobPosting() != null) {

            response.setJobPostingId(
                    match.getJobPosting().getId()
            );

            response.setJobTitle(
                    match.getJobPosting().getTitle()
            );

            if (match.getJobPosting().getCompany() != null) {

                response.setCompanyId(
                        match.getJobPosting()
                                .getCompany()
                                .getId()
                );

                response.setCompanyName(
                        match.getJobPosting()
                                .getCompany()
                                .getName()
                );
            }
        }

        response.setScore(match.getScore());
        response.setStatus(match.getStatus());
        response.setCreatedAt(match.getCreatedAt());
        response.setUpdatedAt(match.getUpdatedAt());

        return response;
    }
}