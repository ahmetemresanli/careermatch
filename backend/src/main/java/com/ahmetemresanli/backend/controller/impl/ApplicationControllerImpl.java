package com.ahmetemresanli.backend.controller.impl;

import com.ahmetemresanli.backend.controller.IApplicationController;
import com.ahmetemresanli.backend.entity.Application;
import com.ahmetemresanli.backend.enums.ApplicationStatus;
import com.ahmetemresanli.backend.service.IApplicationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
public class ApplicationControllerImpl
        implements IApplicationController {

    private final IApplicationService applicationService;

    public ApplicationControllerImpl(
            IApplicationService applicationService
    ) {
        this.applicationService = applicationService;
    }

    @Override
    @PostMapping
    public ResponseEntity<Application> applyToJob(
            @RequestParam Long candidateProfileId,
            @RequestParam Long jobPostingId,
            @RequestParam(required = false) Long resumeId,
            @RequestParam(required = false) String coverLetter
    ) {

        Application application =
                applicationService.applyToJob(
                        candidateProfileId,
                        jobPostingId,
                        resumeId,
                        coverLetter
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(application);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<Application> getApplicationById(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                applicationService
                        .getApplicationById(id)
        );
    }

    @Override
    @GetMapping("/candidate/{candidateProfileId}")
    public ResponseEntity<List<Application>>
    getApplicationsByCandidateProfileId(
            @PathVariable Long candidateProfileId
    ) {

        return ResponseEntity.ok(
                applicationService
                        .getApplicationsByCandidateProfileId(
                                candidateProfileId
                        )
        );
    }

    @Override
    @GetMapping("/job/{jobPostingId}")
    public ResponseEntity<List<Application>>
    getApplicationsByJobPostingId(
            @PathVariable Long jobPostingId
    ) {

        return ResponseEntity.ok(
                applicationService
                        .getApplicationsByJobPostingId(
                                jobPostingId
                        )
        );
    }

    @Override
    @GetMapping("/job/{jobPostingId}/status")
    public ResponseEntity<List<Application>>
    getApplicationsByJobPostingIdAndStatus(
            @PathVariable Long jobPostingId,
            @RequestParam ApplicationStatus status
    ) {

        return ResponseEntity.ok(
                applicationService
                        .getApplicationsByJobPostingIdAndStatus(
                                jobPostingId,
                                status
                        )
        );
    }

    @Override
    @PutMapping("/{applicationId}/status")
    public ResponseEntity<Application>
    updateApplicationStatus(
            @PathVariable Long applicationId,
            @RequestParam ApplicationStatus status
    ) {

        return ResponseEntity.ok(
                applicationService
                        .updateApplicationStatus(
                                applicationId,
                                status
                        )
        );
    }
}