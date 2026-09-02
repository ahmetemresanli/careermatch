package com.ahmetemresanli.backend.controller;

import com.ahmetemresanli.backend.dto.request.CandidateSkillCreateRequest;
import com.ahmetemresanli.backend.dto.response.CandidateSkillResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ICandidateSkillController {

    ResponseEntity<CandidateSkillResponse> addSkillToCandidate(
            Long candidateProfileId,
            Long skillId,
            CandidateSkillCreateRequest request
    );

    ResponseEntity<CandidateSkillResponse> getCandidateSkillById(
            Long id
    );

    ResponseEntity<List<CandidateSkillResponse>>
    getSkillsByCandidateProfileId(
            Long candidateProfileId
    );

    ResponseEntity<List<CandidateSkillResponse>>
    getCandidatesBySkillId(
            Long skillId
    );
}