package com.ahmetemresanli.backend.controller.impl;

import com.ahmetemresanli.backend.controller.IEmploymentVerificationController;
import com.ahmetemresanli.backend.dto.request.EmploymentDocumentVerificationRequest;
import com.ahmetemresanli.backend.dto.request.EmploymentEmailVerificationRequest;
import com.ahmetemresanli.backend.dto.request.VerificationRejectRequest;
import com.ahmetemresanli.backend.dto.response.EmploymentVerificationResponse;
import com.ahmetemresanli.backend.entity.EmploymentVerification;
import com.ahmetemresanli.backend.mapper.EmploymentVerificationMapper;
import com.ahmetemresanli.backend.service.IEmploymentVerificationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employment-verifications")
public class EmploymentVerificationControllerImpl
        implements IEmploymentVerificationController {

    private final IEmploymentVerificationService verificationService;

    public EmploymentVerificationControllerImpl(
            IEmploymentVerificationService verificationService
    ) {
        this.verificationService = verificationService;
    }

    @Override
    @PostMapping("/experience/{experienceId}/email")
    @PreAuthorize("@access.ownsExperience(#experienceId)")
    public ResponseEntity<EmploymentVerificationResponse>
    requestEmailVerification(
            @PathVariable Long experienceId,
            @Valid @RequestBody
            EmploymentEmailVerificationRequest request
    ) {

        EmploymentVerification verification =
                verificationService.requestWorkEmailVerification(
                        experienceId,
                        request.getVerificationEmail()
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        EmploymentVerificationMapper.toResponse(
                                verification
                        )
                );
    }

    @Override
    @PostMapping("/experience/{experienceId}/document")
    @PreAuthorize("@access.ownsExperience(#experienceId)")
    public ResponseEntity<EmploymentVerificationResponse>
    requestDocumentVerification(
            @PathVariable Long experienceId,
            @Valid @RequestBody
            EmploymentDocumentVerificationRequest request
    ) {

        EmploymentVerification verification =
                verificationService.requestDocumentVerification(
                        experienceId,
                        request.getDocumentUrl()
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        EmploymentVerificationMapper.toResponse(
                                verification
                        )
                );
    }

    @Override
    @GetMapping("/verify-email")
    public ResponseEntity<EmploymentVerificationResponse>
    verifyEmail(
            @RequestParam String token
    ) {

        EmploymentVerification verification =
                verificationService.verifyWorkEmail(
                        token
                );

        return ResponseEntity.ok(
                EmploymentVerificationMapper.toResponse(
                        verification
                )
        );
    }

    @Override
    @PutMapping("/{verificationId}/approve")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EmploymentVerificationResponse>
    approveDocument(
            @PathVariable Long verificationId
    ) {

        EmploymentVerification verification =
                verificationService.approveDocument(
                        verificationId
                );

        return ResponseEntity.ok(
                EmploymentVerificationMapper.toResponse(
                        verification
                )
        );
    }

    @Override
    @PutMapping("/{verificationId}/reject")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EmploymentVerificationResponse>
    rejectDocument(
            @PathVariable Long verificationId,
            @Valid @RequestBody VerificationRejectRequest request
    ) {

        EmploymentVerification verification =
                verificationService.rejectDocument(
                        verificationId,
                        request.getRejectionReason()
                );

        return ResponseEntity.ok(
                EmploymentVerificationMapper.toResponse(
                        verification
                )
        );
    }

    @Override
    @GetMapping("/{id}")
    @PreAuthorize("@access.canAccessEmploymentVerification(#id)")
    public ResponseEntity<EmploymentVerificationResponse>
    getVerificationById(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                EmploymentVerificationMapper.toResponse(
                        verificationService
                                .getVerificationById(id)
                )
        );
    }

    @Override
    @GetMapping("/experience/{experienceId}")
    @PreAuthorize("@access.ownsExperience(#experienceId) or hasRole('ADMIN')")
    public ResponseEntity<List<EmploymentVerificationResponse>>
    getVerificationsByExperience(
            @PathVariable Long experienceId
    ) {

        List<EmploymentVerificationResponse> responses =
                verificationService
                        .getVerificationsByExperienceId(
                                experienceId
                        )
                        .stream()
                        .map(
                                EmploymentVerificationMapper::toResponse
                        )
                        .toList();

        return ResponseEntity.ok(responses);
    }
}
