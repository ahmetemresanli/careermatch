package com.ahmetemresanli.backend.controller.impl;

import com.ahmetemresanli.backend.controller.IExperienceController;
import com.ahmetemresanli.backend.dto.request.ExperienceCreateRequest;
import com.ahmetemresanli.backend.dto.response.ExperienceResponse;
import com.ahmetemresanli.backend.entity.EmploymentVerification;
import com.ahmetemresanli.backend.entity.Experience;
import com.ahmetemresanli.backend.mapper.ExperienceMapper;
import com.ahmetemresanli.backend.service.IEmploymentVerificationService;
import com.ahmetemresanli.backend.service.IExperienceService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/experiences")
public class ExperienceControllerImpl
        implements IExperienceController {

    private final IExperienceService experienceService;

    private final IEmploymentVerificationService
            employmentVerificationService;

    public ExperienceControllerImpl(
            IExperienceService experienceService,
            IEmploymentVerificationService employmentVerificationService
    ) {
        this.experienceService = experienceService;
        this.employmentVerificationService =
                employmentVerificationService;
    }

    @Override
    @PostMapping("/candidate/{candidateProfileId}")
    @PreAuthorize("@access.ownsCandidate(#candidateProfileId)")
    public ResponseEntity<ExperienceResponse> createExperience(
            @PathVariable Long candidateProfileId,
            @Valid @RequestBody ExperienceCreateRequest request
    ) {

        Experience experience =
                ExperienceMapper.toEntity(request);

        Experience savedExperience =
                experienceService.createExperience(
                        candidateProfileId,
                        experience
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        ExperienceMapper.toResponse(
                                savedExperience,
                                null
                        )
                );
    }

    @Override
    @GetMapping("/{id}")
    @PreAuthorize("@access.ownsExperience(#id) or hasAnyRole('COMPANY','ADMIN')")
    public ResponseEntity<ExperienceResponse> getExperienceById(
            @PathVariable Long id
    ) {

        Experience experience =
                experienceService.getExperienceById(id);

        EmploymentVerification verification =
                employmentVerificationService
                        .getEffectiveVerification(id)
                        .orElse(null);

        return ResponseEntity.ok(
                ExperienceMapper.toResponse(
                        experience,
                        verification
                )
        );
    }

    @Override
    @GetMapping("/candidate/{candidateProfileId}")
    @PreAuthorize("@access.ownsCandidate(#candidateProfileId) or hasAnyRole('COMPANY','ADMIN')")
    public ResponseEntity<List<ExperienceResponse>>
    getExperiencesByCandidate(
            @PathVariable Long candidateProfileId
    ) {

        List<ExperienceResponse> responses =
                experienceService
                        .getExperiencesByCandidateProfileId(
                                candidateProfileId
                        )
                        .stream()
                        .map(experience -> {

                            EmploymentVerification verification =
                                    employmentVerificationService
                                            .getEffectiveVerification(
                                                    experience.getId()
                                            )
                                            .orElse(null);

                            return ExperienceMapper.toResponse(
                                    experience,
                                    verification
                            );
                        })
                        .toList();

        return ResponseEntity.ok(responses);
    }

    @Override
    @DeleteMapping("/{id}")
    @PreAuthorize("@access.ownsExperience(#id)")
    public ResponseEntity<Void> deleteExperience(
            @PathVariable Long id
    ) {

        experienceService.deleteExperience(id);

        return ResponseEntity.noContent().build();
    }
}
