package com.ahmetemresanli.backend.controller.impl;

import com.ahmetemresanli.backend.controller.IEducationController;
import com.ahmetemresanli.backend.dto.request.EducationCreateRequest;
import com.ahmetemresanli.backend.dto.response.EducationResponse;
import com.ahmetemresanli.backend.entity.Education;
import com.ahmetemresanli.backend.mapper.EducationMapper;
import com.ahmetemresanli.backend.service.IEducationService;
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

    public EducationControllerImpl(
            IEducationService educationService
    ) {
        this.educationService = educationService;
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

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        EducationMapper.toResponse(
                                savedEducation
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

        return ResponseEntity.ok(
                EducationMapper.toResponse(education)
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
                        .map(EducationMapper::toResponse)
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