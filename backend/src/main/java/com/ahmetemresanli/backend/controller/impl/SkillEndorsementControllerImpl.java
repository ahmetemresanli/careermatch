package com.ahmetemresanli.backend.controller.impl;

import com.ahmetemresanli.backend.controller.ISkillEndorsementController;
import com.ahmetemresanli.backend.dto.request.SkillEndorsementAcceptRequest;
import com.ahmetemresanli.backend.dto.request.SkillEndorsementCreateRequest;
import com.ahmetemresanli.backend.dto.response.SkillEndorsementResponse;
import com.ahmetemresanli.backend.entity.SkillEndorsement;
import com.ahmetemresanli.backend.mapper.SkillEndorsementMapper;
import com.ahmetemresanli.backend.service.ISkillEndorsementService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/skill-endorsements")
public class SkillEndorsementControllerImpl
        implements ISkillEndorsementController {

    private final ISkillEndorsementService skillEndorsementService;

    public SkillEndorsementControllerImpl(
            ISkillEndorsementService skillEndorsementService
    ) {
        this.skillEndorsementService =
                skillEndorsementService;
    }

    @Override
    @PostMapping("/candidate-skill/{candidateSkillId}/request")
    public ResponseEntity<SkillEndorsementResponse>
    createEndorsementRequest(
            @PathVariable Long candidateSkillId,
            @Valid @RequestBody
            SkillEndorsementCreateRequest request
    ) {

        SkillEndorsement endorsement =
                skillEndorsementService
                        .createEndorsementRequest(
                                candidateSkillId,
                                request.getEndorserName(),
                                request.getEndorserEmail(),
                                request.getRelation(),
                                request.getRequestMessage()
                        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        SkillEndorsementMapper
                                .toResponse(endorsement)
                );
    }

    @Override
    @PostMapping("/endorse")
    public ResponseEntity<SkillEndorsementResponse>
    endorseSkill(
            @RequestParam String token,
            @Valid @RequestBody
            SkillEndorsementAcceptRequest request
    ) {

        SkillEndorsement endorsement =
                skillEndorsementService
                        .endorseSkill(
                                token,
                                request.getEndorsementComment()
                        );

        return ResponseEntity.ok(
                SkillEndorsementMapper
                        .toResponse(endorsement)
        );
    }

    @Override
    @PostMapping("/reject")
    public ResponseEntity<SkillEndorsementResponse>
    rejectEndorsement(
            @RequestParam String token
    ) {

        SkillEndorsement endorsement =
                skillEndorsementService
                        .rejectEndorsement(token);

        return ResponseEntity.ok(
                SkillEndorsementMapper
                        .toResponse(endorsement)
        );
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<SkillEndorsementResponse>
    getEndorsementById(
            @PathVariable Long id
    ) {

        SkillEndorsement endorsement =
                skillEndorsementService
                        .getEndorsementById(id);

        return ResponseEntity.ok(
                SkillEndorsementMapper
                        .toResponse(endorsement)
        );
    }

    @Override
    @GetMapping("/candidate-skill/{candidateSkillId}")
    public ResponseEntity<List<SkillEndorsementResponse>>
    getEndorsementsByCandidateSkill(
            @PathVariable Long candidateSkillId
    ) {

        List<SkillEndorsementResponse> responses =
                skillEndorsementService
                        .getEndorsementsByCandidateSkill(
                                candidateSkillId
                        )
                        .stream()
                        .map(
                                SkillEndorsementMapper::toResponse
                        )
                        .toList();

        return ResponseEntity.ok(responses);
    }

    @Override
    @GetMapping("/candidate/{candidateProfileId}")
    public ResponseEntity<List<SkillEndorsementResponse>>
    getEndorsementsByCandidate(
            @PathVariable Long candidateProfileId
    ) {

        List<SkillEndorsementResponse> responses =
                skillEndorsementService
                        .getEndorsementsByCandidate(
                                candidateProfileId
                        )
                        .stream()
                        .map(
                                SkillEndorsementMapper::toResponse
                        )
                        .toList();

        return ResponseEntity.ok(responses);
    }

    @Override
    @GetMapping("/candidate/{candidateProfileId}/endorsed")
    public ResponseEntity<List<SkillEndorsementResponse>>
    getApprovedEndorsementsByCandidate(
            @PathVariable Long candidateProfileId
    ) {

        List<SkillEndorsementResponse> responses =
                skillEndorsementService
                        .getApprovedEndorsementsByCandidate(
                                candidateProfileId
                        )
                        .stream()
                        .map(
                                SkillEndorsementMapper::toResponse
                        )
                        .toList();

        return ResponseEntity.ok(responses);
    }
}