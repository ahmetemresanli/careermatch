package com.ahmetemresanli.backend.controller.impl;

import com.ahmetemresanli.backend.controller.IEducationController;
import com.ahmetemresanli.backend.dto.request.EducationCreateRequest;
import com.ahmetemresanli.backend.dto.response.EducationResponse;
import com.ahmetemresanli.backend.entity.Education;
import com.ahmetemresanli.backend.entity.EducationVerification;
import com.ahmetemresanli.backend.mapper.EducationMapper;
import com.ahmetemresanli.backend.service.IEducationService;
import com.ahmetemresanli.backend.service.IEducationVerificationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/educations")
public class EducationControllerImpl
        implements IEducationController {

    private final IEducationService educationService;

    private final IEducationVerificationService
            educationVerificationService;

    public EducationControllerImpl(
            IEducationService educationService,
            IEducationVerificationService educationVerificationService
    ) {
        this.educationService = educationService;
        this.educationVerificationService =
                educationVerificationService;
    }

    @Override
    @PostMapping("/candidate/{candidateProfileId}")
    public ResponseEntity<EducationResponse> createEducation(
            @PathVariable Long candidateProfileId,
            @Valid @RequestBody EducationCreateRequest request
    ) {

        Education education =
                EducationMapper.toEntity(request);

        Education savedEducation =
                educationService.createEducation(
                        candidateProfileId,
                        education
                );

        /*
         * Yeni oluşturulan education'ın henüz
         * verification kaydı yok.
         */
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        EducationMapper.toResponse(
                                savedEducation,
                                null
                        )
                );
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<EducationResponse> getEducationById(
            @PathVariable Long id
    ) {

        Education education =
                educationService.getEducationById(id);

        EducationVerification verification =
                educationVerificationService
                        .getEffectiveVerification(id)
                        .orElse(null);

        return ResponseEntity.ok(
                EducationMapper.toResponse(
                        education,
                        verification
                )
        );
    }

    @Override
    @GetMapping("/candidate/{candidateProfileId}")
    public ResponseEntity<List<EducationResponse>>
    getEducationsByCandidate(
            @PathVariable Long candidateProfileId
    ) {

        List<EducationResponse> responses =
                educationService
                        .getEducationsByCandidateProfileId(
                                candidateProfileId
                        )
                        .stream()
                        .map(education -> {

                            EducationVerification verification =
                                    educationVerificationService
                                            .getEffectiveVerification(
                                                    education.getId()
                                            )
                                            .orElse(null);

                            return EducationMapper.toResponse(
                                    education,
                                    verification
                            );
                        })
                        .toList();

        return ResponseEntity.ok(responses);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEducation(
            @PathVariable Long id
    ) {

        educationService.deleteEducation(id);

        return ResponseEntity.noContent().build();
    }
}