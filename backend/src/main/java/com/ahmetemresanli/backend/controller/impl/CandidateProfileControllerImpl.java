package com.ahmetemresanli.backend.controller.impl;

import com.ahmetemresanli.backend.controller.ICandidateProfileController;
import com.ahmetemresanli.backend.dto.request.CandidateProfileCreateRequest;
import com.ahmetemresanli.backend.dto.response.CandidateProfileResponse;
import com.ahmetemresanli.backend.entity.CandidateProfile;
import com.ahmetemresanli.backend.mapper.CandidateProfileMapper;
import com.ahmetemresanli.backend.service.ICandidateProfileService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/candidate-profiles")
public class CandidateProfileControllerImpl
        implements ICandidateProfileController {

    private final ICandidateProfileService candidateProfileService;

    public CandidateProfileControllerImpl(
            ICandidateProfileService candidateProfileService
    ) {
        this.candidateProfileService = candidateProfileService;
    }

    @Override
    @PostMapping("/user/{userId}")
    public ResponseEntity<CandidateProfileResponse>
    createCandidateProfile(
            @PathVariable Long userId,
            @Valid @RequestBody CandidateProfileCreateRequest request
    ) {

        CandidateProfile candidateProfile =
                CandidateProfileMapper.toEntity(request);

        CandidateProfile createdCandidateProfile =
                candidateProfileService.createCandidateProfile(
                        userId,
                        candidateProfile
                );

        CandidateProfileResponse response =
                CandidateProfileMapper.toResponse(
                        createdCandidateProfile
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<CandidateProfileResponse>
    getCandidateProfileById(
            @PathVariable Long id
    ) {

        CandidateProfile candidateProfile =
                candidateProfileService
                        .getCandidateProfileById(id);

        return ResponseEntity.ok(
                CandidateProfileMapper.toResponse(
                        candidateProfile
                )
        );
    }

    @Override
    @GetMapping("/user/{userId}")
    public ResponseEntity<CandidateProfileResponse>
    getCandidateProfileByUserId(
            @PathVariable Long userId
    ) {

        CandidateProfile candidateProfile =
                candidateProfileService
                        .getCandidateProfileByUserId(userId);

        return ResponseEntity.ok(
                CandidateProfileMapper.toResponse(
                        candidateProfile
                )
        );
    }

    @Override
    @GetMapping
    public ResponseEntity<List<CandidateProfileResponse>>
    getAllCandidateProfiles() {

        List<CandidateProfileResponse> responses =
                candidateProfileService
                        .getAllCandidateProfiles()
                        .stream()
                        .map(CandidateProfileMapper::toResponse)
                        .toList();

        return ResponseEntity.ok(responses);
    }
}