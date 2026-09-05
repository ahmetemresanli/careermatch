package com.ahmetemresanli.backend.controller.impl;

import com.ahmetemresanli.backend.controller.IReferenceController;
import com.ahmetemresanli.backend.dto.request.ReferenceAcceptRequest;
import com.ahmetemresanli.backend.dto.request.ReferenceRequestCreateRequest;
import com.ahmetemresanli.backend.dto.response.ReferenceRequestResponse;
import com.ahmetemresanli.backend.dto.response.ReferenceResponse;
import com.ahmetemresanli.backend.entity.Reference;
import com.ahmetemresanli.backend.entity.ReferenceRequest;
import com.ahmetemresanli.backend.mapper.ReferenceMapper;
import com.ahmetemresanli.backend.mapper.ReferenceRequestMapper;
import com.ahmetemresanli.backend.service.IReferenceService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/references")
public class ReferenceControllerImpl
        implements IReferenceController {

    private final IReferenceService referenceService;

    public ReferenceControllerImpl(
            IReferenceService referenceService
    ) {
        this.referenceService = referenceService;
    }

    @Override
    @PostMapping("/candidate/{candidateProfileId}/request")
    @PreAuthorize("@access.ownsCandidate(#candidateProfileId)")
    public ResponseEntity<ReferenceRequestResponse> createReferenceRequest(
            @PathVariable Long candidateProfileId,
            @Valid @RequestBody ReferenceRequestCreateRequest request
    ) {

        ReferenceRequest referenceRequest =
                referenceService.createReferenceRequest(
                        candidateProfileId,
                        request.getReferenceName(),
                        request.getReferenceEmail(),
                        request.getRelation(),
                        request.getRequestMessage()
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        ReferenceRequestMapper.toResponse(
                                referenceRequest
                        )
                );
    }

    @Override
    @PostMapping("/accept")
    public ResponseEntity<ReferenceResponse> acceptReferenceRequest(
            @RequestParam String token,
            @Valid @RequestBody ReferenceAcceptRequest request
    ) {

        Reference reference =
                referenceService.acceptReferenceRequest(
                        token,
                        request.getReferenceText(),
                        request.getOrganizationName(),
                        request.getPositionTitle()
                );

        return ResponseEntity.ok(
                ReferenceMapper.toResponse(reference)
        );
    }

    @Override
    @PostMapping("/reject")
    public ResponseEntity<ReferenceRequestResponse> rejectReferenceRequest(
            @RequestParam String token
    ) {

        ReferenceRequest referenceRequest =
                referenceService.rejectReferenceRequest(
                        token
                );

        return ResponseEntity.ok(
                ReferenceRequestMapper.toResponse(
                        referenceRequest
                )
        );
    }

    @Override
    @GetMapping("/requests/{id}")
    @PreAuthorize("@access.ownsReferenceRequest(#id)")
    public ResponseEntity<ReferenceRequestResponse> getReferenceRequestById(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                ReferenceRequestMapper.toResponse(
                        referenceService
                                .getReferenceRequestById(id)
                )
        );
    }

    @Override
    @GetMapping("/requests/candidate/{candidateProfileId}")
    @PreAuthorize("@access.ownsCandidate(#candidateProfileId)")
    public ResponseEntity<List<ReferenceRequestResponse>>
    getReferenceRequestsByCandidate(
            @PathVariable Long candidateProfileId
    ) {

        List<ReferenceRequestResponse> responses =
                referenceService
                        .getReferenceRequestsByCandidate(
                                candidateProfileId
                        )
                        .stream()
                        .map(ReferenceRequestMapper::toResponse)
                        .toList();

        return ResponseEntity.ok(responses);
    }

    @Override
    @GetMapping("/{id}")
    @PreAuthorize("@access.ownsReference(#id) or hasAnyRole('COMPANY','ADMIN')")
    public ResponseEntity<ReferenceResponse> getReferenceById(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                ReferenceMapper.toResponse(
                        referenceService
                                .getReferenceById(id)
                )
        );
    }

    @Override
    @GetMapping("/candidate/{candidateProfileId}")
    @PreAuthorize("@access.ownsCandidate(#candidateProfileId)")
    public ResponseEntity<List<ReferenceResponse>> getReferencesByCandidate(
            @PathVariable Long candidateProfileId
    ) {

        List<ReferenceResponse> responses =
                referenceService
                        .getReferencesByCandidate(
                                candidateProfileId
                        )
                        .stream()
                        .map(ReferenceMapper::toResponse)
                        .toList();

        return ResponseEntity.ok(responses);
    }

    @Override
    @GetMapping("/candidate/{candidateProfileId}/visible")
    @PreAuthorize("@access.ownsCandidate(#candidateProfileId) or hasAnyRole('COMPANY','ADMIN')")
    public ResponseEntity<List<ReferenceResponse>>
    getVisibleReferencesByCandidate(
            @PathVariable Long candidateProfileId
    ) {

        List<ReferenceResponse> responses =
                referenceService
                        .getVisibleReferencesByCandidate(
                                candidateProfileId
                        )
                        .stream()
                        .map(ReferenceMapper::toResponse)
                        .toList();

        return ResponseEntity.ok(responses);
    }
}
