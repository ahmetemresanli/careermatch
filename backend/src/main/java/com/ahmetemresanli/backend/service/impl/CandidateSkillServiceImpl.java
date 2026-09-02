package com.ahmetemresanli.backend.service.impl;

import com.ahmetemresanli.backend.entity.CandidateProfile;
import com.ahmetemresanli.backend.entity.CandidateSkill;
import com.ahmetemresanli.backend.entity.Skill;
import com.ahmetemresanli.backend.enums.SkillLevel;
import com.ahmetemresanli.backend.exception.BusinessException;
import com.ahmetemresanli.backend.exception.DuplicateResourceException;
import com.ahmetemresanli.backend.exception.ResourceNotFoundException;
import com.ahmetemresanli.backend.repository.CandidateProfileRepository;
import com.ahmetemresanli.backend.repository.CandidateSkillRepository;
import com.ahmetemresanli.backend.repository.SkillRepository;
import com.ahmetemresanli.backend.service.ICandidateSkillService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CandidateSkillServiceImpl
        implements ICandidateSkillService {

    private final CandidateSkillRepository candidateSkillRepository;
    private final CandidateProfileRepository candidateProfileRepository;
    private final SkillRepository skillRepository;

    public CandidateSkillServiceImpl(
            CandidateSkillRepository candidateSkillRepository,
            CandidateProfileRepository candidateProfileRepository,
            SkillRepository skillRepository
    ) {
        this.candidateSkillRepository = candidateSkillRepository;
        this.candidateProfileRepository = candidateProfileRepository;
        this.skillRepository = skillRepository;
    }

    @Override
    public CandidateSkill addSkillToCandidate(
            Long candidateProfileId,
            Long skillId,
            SkillLevel skillLevel,
            Integer yearsOfExperience
    ) {

        CandidateProfile candidateProfile =
                candidateProfileRepository.findById(candidateProfileId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Candidate profile not found"
                                )
                        );

        Skill skill = skillRepository.findById(skillId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Skill not found"
                        )
                );

        if (candidateSkillRepository
                .existsByCandidateProfileIdAndSkillId(
                        candidateProfileId,
                        skillId
                )) {

            throw new DuplicateResourceException(
                    "Candidate already has this skill"
            );
        }

        if (skillLevel == null) {
            throw new BusinessException(
                    "Skill level cannot be empty"
            );
        }

        if (yearsOfExperience != null
                && yearsOfExperience < 0) {

            throw new BusinessException(
                    "Years of experience cannot be negative"
            );
        }

        CandidateSkill candidateSkill =
                new CandidateSkill();

        candidateSkill.setCandidateProfile(candidateProfile);
        candidateSkill.setSkill(skill);
        candidateSkill.setSkillLevel(skillLevel);
        candidateSkill.setYearsOfExperience(yearsOfExperience);

        return candidateSkillRepository.save(candidateSkill);
    }

    @Override
    public CandidateSkill getCandidateSkillById(Long id) {

        return candidateSkillRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Candidate skill not found"
                        )
                );
    }

    @Override
    public List<CandidateSkill> getSkillsByCandidateProfileId(
            Long candidateProfileId
    ) {

        return candidateSkillRepository
                .findByCandidateProfileId(candidateProfileId);
    }

    @Override
    public List<CandidateSkill> getCandidatesBySkillId(
            Long skillId
    ) {

        return candidateSkillRepository
                .findBySkillId(skillId);
    }
}