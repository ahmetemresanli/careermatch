package com.ahmetemresanli.backend.service;

import com.ahmetemresanli.backend.entity.CandidateSkill;
import com.ahmetemresanli.backend.enums.SkillLevel;

import java.util.List;

public interface ICandidateSkillService {

    CandidateSkill addSkillToCandidate(
            Long candidateProfileId,
            Long skillId,
            SkillLevel skillLevel,
            Integer yearsOfExperience
    );

    CandidateSkill getCandidateSkillById(Long id);

    List<CandidateSkill> getSkillsByCandidateProfileId(
            Long candidateProfileId
    );

    List<CandidateSkill> getCandidatesBySkillId(Long skillId);
}