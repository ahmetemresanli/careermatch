package com.ahmetemresanli.backend.controller;

import com.ahmetemresanli.backend.dto.request.SkillEndorsementAcceptRequest;
import com.ahmetemresanli.backend.dto.request.SkillEndorsementCreateRequest;
import com.ahmetemresanli.backend.dto.response.SkillEndorsementResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ISkillEndorsementController {

    ResponseEntity<SkillEndorsementResponse>
    createEndorsementRequest(
            Long candidateSkillId,
            SkillEndorsementCreateRequest request
    );

    ResponseEntity<SkillEndorsementResponse>
    endorseSkill(
            String token,
            SkillEndorsementAcceptRequest request
    );

    ResponseEntity<SkillEndorsementResponse>
    rejectEndorsement(
            String token
    );

    ResponseEntity<SkillEndorsementResponse>
    getEndorsementById(
            Long id
    );

    ResponseEntity<List<SkillEndorsementResponse>>
    getEndorsementsByCandidateSkill(
            Long candidateSkillId
    );

    ResponseEntity<List<SkillEndorsementResponse>>
    getEndorsementsByCandidate(
            Long candidateProfileId
    );

    ResponseEntity<List<SkillEndorsementResponse>>
    getApprovedEndorsementsByCandidate(
            Long candidateProfileId
    );
}