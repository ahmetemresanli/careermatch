package com.ahmetemresanli.backend.mapper;

import com.ahmetemresanli.backend.dto.request.CandidateSkillCreateRequest;
import com.ahmetemresanli.backend.dto.response.CandidateSkillResponse;
import com.ahmetemresanli.backend.entity.CandidateSkill;

public final class CandidateSkillMapper {

    private CandidateSkillMapper() {
    }

    public static CandidateSkill toEntity(
            CandidateSkillCreateRequest request
    ) {

        if (request == null) {
            return null;
        }

        CandidateSkill candidateSkill =
                new CandidateSkill();

        candidateSkill.setSkillLevel(
                request.getSkillLevel()
        );

        candidateSkill.setYearsOfExperience(
                request.getYearsOfExperience()
        );

        return candidateSkill;
    }

    public static CandidateSkillResponse toResponse(
            CandidateSkill candidateSkill
    ) {

        if (candidateSkill == null) {
            return null;
        }

        CandidateSkillResponse response =
                new CandidateSkillResponse();

        response.setId(candidateSkill.getId());

        if (candidateSkill.getCandidateProfile() != null) {
            response.setCandidateProfileId(
                    candidateSkill
                            .getCandidateProfile()
                            .getId()
            );
        }

        if (candidateSkill.getSkill() != null) {

            response.setSkillId(
                    candidateSkill
                            .getSkill()
                            .getId()
            );

            response.setSkillName(
                    candidateSkill
                            .getSkill()
                            .getName()
            );
        }

        response.setSkillLevel(
                candidateSkill.getSkillLevel()
        );

        response.setYearsOfExperience(
                candidateSkill.getYearsOfExperience()
        );

        response.setCreatedAt(
                candidateSkill.getCreatedAt()
        );

        response.setUpdatedAt(
                candidateSkill.getUpdatedAt()
        );

        return response;
    }
}