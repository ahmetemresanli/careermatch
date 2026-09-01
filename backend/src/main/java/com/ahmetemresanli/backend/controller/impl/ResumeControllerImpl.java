package com.ahmetemresanli.backend.controller.impl;

import com.ahmetemresanli.backend.controller.IResumeController;
import com.ahmetemresanli.backend.entity.Resume;
import com.ahmetemresanli.backend.service.IResumeService;
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
    public ResponseEntity<Resume> createResume(
            @PathVariable Long candidateProfileId,
            @RequestBody Resume resume
    ) {

        Resume createdResume =
                resumeService.createResume(
                        candidateProfileId,
                        resume
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdResume);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<Resume> getResumeById(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                resumeService.getResumeById(id)
        );
    }

    @Override
    @GetMapping("/candidate/{candidateProfileId}")
    public ResponseEntity<List<Resume>>
    getResumesByCandidateProfileId(
            @PathVariable Long candidateProfileId
    ) {

        return ResponseEntity.ok(
                resumeService
                        .getResumesByCandidateProfileId(
                                candidateProfileId
                        )
        );
    }

    @Override
    @PutMapping("/{resumeId}/default")
    public ResponseEntity<Resume> setDefaultResume(
            @RequestParam Long candidateProfileId,
            @PathVariable Long resumeId
    ) {

        return ResponseEntity.ok(
                resumeService.setDefaultResume(
                        candidateProfileId,
                        resumeId
                )
        );
    }
}