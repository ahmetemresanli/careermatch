package com.ahmetemresanli.backend.controller.impl;

import com.ahmetemresanli.backend.controller.IResumeController;
import com.ahmetemresanli.backend.dto.request.ResumeCreateRequest;
import com.ahmetemresanli.backend.dto.response.ResumeResponse;
import com.ahmetemresanli.backend.entity.Resume;
import com.ahmetemresanli.backend.mapper.ResumeMapper;
import com.ahmetemresanli.backend.service.IResumeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/resumes")
public class ResumeControllerImpl
        implements IResumeController {

    private final IResumeService resumeService;

    public ResumeControllerImpl(
            IResumeService resumeService
    ) {
        this.resumeService = resumeService;
    }

    @Override
    @PostMapping("/candidate/{candidateProfileId}")
    public ResponseEntity<ResumeResponse> createResume(
            @PathVariable Long candidateProfileId,
            @Valid @RequestBody ResumeCreateRequest request
    ) {

        Resume resume =
                ResumeMapper.toEntity(request);

        Resume createdResume =
                resumeService.createResume(
                        candidateProfileId,
                        resume
                );

        ResumeResponse response =
                ResumeMapper.toResponse(createdResume);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<ResumeResponse> getResumeById(
            @PathVariable Long id
    ) {

        Resume resume =
                resumeService.getResumeById(id);

        return ResponseEntity.ok(
                ResumeMapper.toResponse(resume)
        );
    }

    @Override
    @GetMapping("/candidate/{candidateProfileId}")
    public ResponseEntity<List<ResumeResponse>>
    getResumesByCandidateProfileId(
            @PathVariable Long candidateProfileId
    ) {

        List<ResumeResponse> responses =
                resumeService
                        .getResumesByCandidateProfileId(
                                candidateProfileId
                        )
                        .stream()
                        .map(ResumeMapper::toResponse)
                        .toList();

        return ResponseEntity.ok(responses);
    }

    @Override
    @PutMapping("/{resumeId}/default")
    public ResponseEntity<ResumeResponse> setDefaultResume(
            @RequestParam Long candidateProfileId,
            @PathVariable Long resumeId
    ) {

        Resume resume =
                resumeService.setDefaultResume(
                        candidateProfileId,
                        resumeId
                );

        return ResponseEntity.ok(
                ResumeMapper.toResponse(resume)
        );
    }
}