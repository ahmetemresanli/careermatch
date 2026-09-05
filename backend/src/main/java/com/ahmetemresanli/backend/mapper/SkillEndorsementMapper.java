package com.ahmetemresanli.backend.mapper;

import com.ahmetemresanli.backend.dto.response.SkillEndorsementResponse;
import com.ahmetemresanli.backend.entity.CandidateSkill;
import com.ahmetemresanli.backend.entity.SkillEndorsement;

public final class SkillEndorsementMapper {

    private SkillEndorsementMapper() {
    }

    public static SkillEndorsementResponse toResponse(
            SkillEndorsement endorsement
    ) {

        SkillEndorsementResponse response =
                new SkillEndorsementResponse();

        response.setId(
                endorsement.getId()
        );

        CandidateSkill candidateSkill =
                endorsement.getCandidateSkill();

        if (candidateSkill != null) {

            response.setCandidateSkillId(
                    candidateSkill.getId()
            );

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
        }

        response.setEndorserName(
                endorsement.getEndorserName()
        );

        response.setEndorserEmail(
                endorsement.getEndorserEmail()
        );

        response.setRelation(
                endorsement.getRelation()
        );

        response.setRequestMessage(
                endorsement.getRequestMessage()
        );

        response.setEndorsementComment(
                endorsement.getEndorsementComment()
        );

        response.setStatus(
                endorsement.getStatus()
        );

        response.setToken(null);

        response.setExpiresAt(
                endorsement.getExpiresAt()
        );

        response.setRespondedAt(
                endorsement.getRespondedAt()
        );

        response.setCreatedAt(
                endorsement.getCreatedAt()
        );

        response.setUpdatedAt(
                endorsement.getUpdatedAt()
        );

        return response;
    }
}
