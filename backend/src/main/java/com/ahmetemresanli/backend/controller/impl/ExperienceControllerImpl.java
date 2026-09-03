package com.ahmetemresanli.backend.controller.impl;

import com.ahmetemresanli.backend.controller.IExperienceController;
import com.ahmetemresanli.backend.dto.request.ExperienceCreateRequest;
import com.ahmetemresanli.backend.dto.response.ExperienceResponse;
import com.ahmetemresanli.backend.entity.Experience;
import com.ahmetemresanli.backend.mapper.ExperienceMapper;
import com.ahmetemresanli.backend.service.IExperienceService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/experiences")
public class ExperienceControllerImpl
        implements IExperienceController {

    private final IExperienceService experienceService;

    public ExperienceControllerImpl(
            IExperienceService experienceService
    ) {
        this.experienceService = experienceService;
    }

    @Override
    @PostMapping("/candidate/{candidateProfileId}")
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
                                savedExperience
                        )
                );
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<ExperienceResponse> getExperienceById(
            @PathVariable Long id
    ) {

        Experience experience =
                experienceService.getExperienceById(id);

        return ResponseEntity.ok(
                ExperienceMapper.toResponse(experience)
        );
    }

    @Override
    @GetMapping("/candidate/{candidateProfileId}")
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
                        .map(ExperienceMapper::toResponse)
                        .toList();

        return ResponseEntity.ok(responses);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExperience(
            @PathVariable Long id
    ) {

        experienceService.deleteExperience(id);

        return ResponseEntity.noContent().build();
    }
}