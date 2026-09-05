package com.ahmetemresanli.backend.service;

import com.ahmetemresanli.backend.entity.SkillEndorsement;
import com.ahmetemresanli.backend.enums.ReferenceRelation;

import java.util.List;

public interface ISkillEndorsementService {

    SkillEndorsement createEndorsementRequest(
            Long candidateSkillId,
            String endorserName,
            String endorserEmail,
            ReferenceRelation relation,
            String requestMessage
    );

    SkillEndorsement endorseSkill(
            String token,
            String endorsementComment
    );

    SkillEndorsement rejectEndorsement(
            String token
    );

    SkillEndorsement getEndorsementById(
            Long id
    );

    List<SkillEndorsement> getEndorsementsByCandidateSkill(
            Long candidateSkillId
    );

    List<SkillEndorsement> getEndorsementsByCandidate(
            Long candidateProfileId
    );

    List<SkillEndorsement> getApprovedEndorsementsByCandidate(
            Long candidateProfileId
    );

    SkillEndorsement endorseDirectly(Long candidateSkillId, Long endorserUserId, ReferenceRelation relation, String comment);
}
