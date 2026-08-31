package com.ahmetemresanli.backend.controller.impl;

import com.ahmetemresanli.backend.controller.ICandidateSkillController;
import com.ahmetemresanli.backend.entity.CandidateSkill;
import com.ahmetemresanli.backend.enums.SkillLevel;
import com.ahmetemresanli.backend.service.ICandidateSkillService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/candidate-skills")
public class CandidateSkillControllerImpl
        implements ICandidateSkillController {

    private final ICandidateSkillService candidateSkillService;

    public CandidateSkillControllerImpl(
            ICandidateSkillService candidateSkillService
    ) {
        this.candidateSkillService = candidateSkillService;
    }

    @Override
    @PostMapping(
            "/candidate/{candidateProfileId}/skill/{skillId}"
    )
    public ResponseEntity<CandidateSkill> addSkillToCandidate(
            @PathVariable Long candidateProfileId,
            @PathVariable Long skillId,
            @RequestParam SkillLevel skillLevel,
            @RequestParam(required = false) Integer yearsOfExperience
    ) {

        CandidateSkill candidateSkill =
                candidateSkillService.addSkillToCandidate(
                        candidateProfileId,
                        skillId,
                        skillLevel,
                        yearsOfExperience
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(candidateSkill);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<CandidateSkill> getCandidateSkillById(
            @PathVariable Long id
    ) {

        CandidateSkill candidateSkill =
                candidateSkillService.getCandidateSkillById(id);

        return ResponseEntity.ok(candidateSkill);
    }

    @Override
    @GetMapping("/candidate/{candidateProfileId}")
    public ResponseEntity<List<CandidateSkill>>
    getSkillsByCandidateProfileId(
            @PathVariable Long candidateProfileId
    ) {

        List<CandidateSkill> candidateSkills =
                candidateSkillService
                        .getSkillsByCandidateProfileId(
                                candidateProfileId
                        );

        return ResponseEntity.ok(candidateSkills);
    }

    @Override
    @GetMapping("/skill/{skillId}")
    public ResponseEntity<List<CandidateSkill>>
    getCandidatesBySkillId(
            @PathVariable Long skillId
    ) {

        List<CandidateSkill> candidateSkills =
                candidateSkillService
                        .getCandidatesBySkillId(skillId);

        return ResponseEntity.ok(candidateSkills);
    }
}