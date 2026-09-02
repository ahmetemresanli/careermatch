package com.ahmetemresanli.backend.controller.impl;

import com.ahmetemresanli.backend.controller.ICandidateSkillController;
import com.ahmetemresanli.backend.dto.request.CandidateSkillCreateRequest;
import com.ahmetemresanli.backend.dto.response.CandidateSkillResponse;
import com.ahmetemresanli.backend.entity.CandidateSkill;
import com.ahmetemresanli.backend.mapper.CandidateSkillMapper;
import com.ahmetemresanli.backend.service.ICandidateSkillService;
import jakarta.validation.Valid;
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
    public ResponseEntity<CandidateSkillResponse>
    addSkillToCandidate(
            @PathVariable Long candidateProfileId,
            @PathVariable Long skillId,
            @Valid @RequestBody CandidateSkillCreateRequest request
    ) {

        CandidateSkill candidateSkill =
                candidateSkillService.addSkillToCandidate(
                        candidateProfileId,
                        skillId,
                        request.getSkillLevel(),
                        request.getYearsOfExperience()
                );

        CandidateSkillResponse response =
                CandidateSkillMapper.toResponse(
                        candidateSkill
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<CandidateSkillResponse>
    getCandidateSkillById(
            @PathVariable Long id
    ) {

        CandidateSkill candidateSkill =
                candidateSkillService
                        .getCandidateSkillById(id);

        return ResponseEntity.ok(
                CandidateSkillMapper.toResponse(
                        candidateSkill
                )
        );
    }

    @Override
    @GetMapping("/candidate/{candidateProfileId}")
    public ResponseEntity<List<CandidateSkillResponse>>
    getSkillsByCandidateProfileId(
            @PathVariable Long candidateProfileId
    ) {

        List<CandidateSkillResponse> responses =
                candidateSkillService
                        .getSkillsByCandidateProfileId(
                                candidateProfileId
                        )
                        .stream()
                        .map(CandidateSkillMapper::toResponse)
                        .toList();

        return ResponseEntity.ok(responses);
    }

    @Override
    @GetMapping("/skill/{skillId}")
    public ResponseEntity<List<CandidateSkillResponse>>
    getCandidatesBySkillId(
            @PathVariable Long skillId
    ) {

        List<CandidateSkillResponse> responses =
                candidateSkillService
                        .getCandidatesBySkillId(skillId)
                        .stream()
                        .map(CandidateSkillMapper::toResponse)
                        .toList();

        return ResponseEntity.ok(responses);
    }
}