package com.ahmetemresanli.backend.controller.impl;

import com.ahmetemresanli.backend.controller.IEducationVerificationController;
import com.ahmetemresanli.backend.dto.request.EducationDocumentVerificationRequest;
import com.ahmetemresanli.backend.dto.request.EducationEmailVerificationRequest;
import com.ahmetemresanli.backend.dto.request.VerificationRejectRequest;
import com.ahmetemresanli.backend.dto.response.EducationVerificationResponse;
import com.ahmetemresanli.backend.entity.EducationVerification;
import com.ahmetemresanli.backend.mapper.EducationVerificationMapper;
import com.ahmetemresanli.backend.service.IEducationVerificationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/education-verifications")
public class EducationVerificationControllerImpl
        implements IEducationVerificationController {

    private final IEducationVerificationService verificationService;

    public EducationVerificationControllerImpl(
            IEducationVerificationService verificationService
    ) {
        this.verificationService = verificationService;
    }

    @Override
    @PostMapping("/education/{educationId}/email")
    public ResponseEntity<EducationVerificationResponse>
    requestEmailVerification(
            @PathVariable Long educationId,
            @Valid @RequestBody
            EducationEmailVerificationRequest request
    ) {

        EducationVerification verification =
                verificationService
                        .requestUniversityEmailVerification(
                                educationId,
                                request.getVerificationEmail()
                        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        EducationVerificationMapper.toResponse(
                                verification
                        )
                );
    }

    @Override
    @PostMapping("/education/{educationId}/document")
    public ResponseEntity<EducationVerificationResponse>
    requestDocumentVerification(
            @PathVariable Long educationId,
            @Valid @RequestBody
            EducationDocumentVerificationRequest request
    ) {

        EducationVerification verification =
                verificationService
                        .requestDocumentVerification(
                                educationId,
                                request.getDocumentUrl()
                        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        EducationVerificationMapper.toResponse(
                                verification
                        )
                );
    }

    @Override
    @GetMapping("/verify-email")
    public ResponseEntity<EducationVerificationResponse>
    verifyEmail(
            @RequestParam String token
    ) {

        EducationVerification verification =
                verificationService
                        .verifyUniversityEmail(token);

        return ResponseEntity.ok(
                EducationVerificationMapper.toResponse(
                        verification
                )
        );
    }

    @Override
    @PutMapping("/{verificationId}/approve")
    public ResponseEntity<EducationVerificationResponse>
    approveDocument(
            @PathVariable Long verificationId
    ) {

        EducationVerification verification =
                verificationService
                        .approveDocument(
                                verificationId
                        );

        return ResponseEntity.ok(
                EducationVerificationMapper.toResponse(
                        verification
                )
        );
    }

    @Override
    @PutMapping("/{verificationId}/reject")
    public ResponseEntity<EducationVerificationResponse>
    rejectDocument(
            @PathVariable Long verificationId,
            @Valid @RequestBody
            VerificationRejectRequest request
    ) {

        EducationVerification verification =
                verificationService
                        .rejectDocument(
                                verificationId,
                                request.getRejectionReason()
                        );

        return ResponseEntity.ok(
                EducationVerificationMapper.toResponse(
                        verification
                )
        );
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<EducationVerificationResponse>
    getVerificationById(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                EducationVerificationMapper.toResponse(
                        verificationService
                                .getVerificationById(id)
                )
        );
    }

    @Override
    @GetMapping("/education/{educationId}")
    public ResponseEntity<List<EducationVerificationResponse>>
    getVerificationsByEducation(
            @PathVariable Long educationId
    ) {

        List<EducationVerificationResponse> responses =
                verificationService
                        .getVerificationsByEducationId(
                                educationId
                        )
                        .stream()
                        .map(
                                EducationVerificationMapper::toResponse
                        )
                        .toList();

        return ResponseEntity.ok(responses);
    }
}