package com.ahmetemresanli.backend.controller.impl;

import com.ahmetemresanli.backend.controller.ICandidateProfileController;
import com.ahmetemresanli.backend.dto.request.CandidateProfileCreateRequest;
import com.ahmetemresanli.backend.dto.request.CandidateProfileUpdateRequest;
import com.ahmetemresanli.backend.dto.response.CandidateProfileResponse;
import com.ahmetemresanli.backend.entity.CandidateProfile;
import com.ahmetemresanli.backend.mapper.CandidateProfileMapper;
import com.ahmetemresanli.backend.service.ICandidateProfileService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasRole('CANDIDATE') and @access.isSelf(#userId)")
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
    @PreAuthorize("@access.ownsCandidate(#id) or hasAnyRole('COMPANY','ADMIN')")
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
    @PreAuthorize("@access.isSelf(#userId) or hasAnyRole('COMPANY','ADMIN')")
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
    @PreAuthorize("hasAnyRole('COMPANY','ADMIN')")
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

    @Override
    @PutMapping("/{id}")
    @PreAuthorize("@access.ownsCandidate(#id)")
    public ResponseEntity<CandidateProfileResponse> updateCandidateProfile(
            @PathVariable Long id, @Valid @RequestBody CandidateProfileUpdateRequest request) {
        CandidateProfile profile = candidateProfileService.getCandidateProfileById(id);
        CandidateProfileMapper.applyUpdate(profile, request);
        return ResponseEntity.ok(CandidateProfileMapper.toResponse(candidateProfileService.updateCandidateProfile(id, profile)));
    }
}
