package com.ahmetemresanli.backend.controller;

import com.ahmetemresanli.backend.entity.CandidateSkill;
import com.ahmetemresanli.backend.enums.SkillLevel;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ICandidateSkillController {

    ResponseEntity<CandidateSkill> addSkillToCandidate(
            Long candidateProfileId,
            Long skillId,
            SkillLevel skillLevel,
            Integer yearsOfExperience
    );

    ResponseEntity<CandidateSkill> getCandidateSkillById(Long id);

    ResponseEntity<List<CandidateSkill>> getSkillsByCandidateProfileId(
            Long candidateProfileId
    );

    ResponseEntity<List<CandidateSkill>> getCandidatesBySkillId(
            Long skillId
    );
}