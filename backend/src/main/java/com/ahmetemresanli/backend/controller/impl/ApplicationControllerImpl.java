package com.ahmetemresanli.backend.controller.impl;

import com.ahmetemresanli.backend.controller.IApplicationController;
import com.ahmetemresanli.backend.dto.request.ApplicationCreateRequest;
import com.ahmetemresanli.backend.dto.request.ApplicationStatusUpdateRequest;
import com.ahmetemresanli.backend.dto.response.ApplicationResponse;
import com.ahmetemresanli.backend.entity.Application;
import com.ahmetemresanli.backend.enums.ApplicationStatus;
import com.ahmetemresanli.backend.mapper.ApplicationMapper;
import com.ahmetemresanli.backend.service.IApplicationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PostMapping(
            "/candidate/{candidateProfileId}/job/{jobPostingId}"
    )
    @PreAuthorize("hasRole('CANDIDATE') and @access.ownsCandidate(#candidateProfileId)")
    public ResponseEntity<ApplicationResponse> applyToJob(
            @PathVariable Long candidateProfileId,
            @PathVariable Long jobPostingId,
            @Valid @RequestBody ApplicationCreateRequest request
    ) {

        Application application =
                applicationService.applyToJob(
                        candidateProfileId,
                        jobPostingId,
                        request.getResumeId(),
                        request.getCoverLetter()
                );

        ApplicationResponse response =
                ApplicationMapper.toResponse(application);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @Override
    @GetMapping("/{id}")
    @PreAuthorize("@access.canAccessApplication(#id)")
    public ResponseEntity<ApplicationResponse>
    getApplicationById(
            @PathVariable Long id
    ) {

        Application application =
                applicationService.getApplicationById(id);

        return ResponseEntity.ok(
                ApplicationMapper.toResponse(application)
        );
    }

    @Override
    @GetMapping("/candidate/{candidateProfileId}")
    @PreAuthorize("@access.ownsCandidate(#candidateProfileId)")
    public ResponseEntity<List<ApplicationResponse>>
    getApplicationsByCandidateProfileId(
            @PathVariable Long candidateProfileId
    ) {

        List<ApplicationResponse> responses =
                applicationService
                        .getApplicationsByCandidateProfileId(
                                candidateProfileId
                        )
                        .stream()
                        .map(ApplicationMapper::toResponse)
                        .toList();

        return ResponseEntity.ok(responses);
    }

    @Override
    @GetMapping("/job/{jobPostingId}")
    @PreAuthorize("@access.managesJob(#jobPostingId)")
    public ResponseEntity<List<ApplicationResponse>>
    getApplicationsByJobPostingId(
            @PathVariable Long jobPostingId
    ) {

        List<ApplicationResponse> responses =
                applicationService
                        .getApplicationsByJobPostingId(
                                jobPostingId
                        )
                        .stream()
                        .map(ApplicationMapper::toResponse)
                        .toList();

        return ResponseEntity.ok(responses);
    }

    @Override
    @GetMapping("/job/{jobPostingId}/status")
    @PreAuthorize("@access.managesJob(#jobPostingId)")
    public ResponseEntity<List<ApplicationResponse>>
    getApplicationsByJobPostingIdAndStatus(
            @PathVariable Long jobPostingId,
            @RequestParam ApplicationStatus status
    ) {

        List<ApplicationResponse> responses =
                applicationService
                        .getApplicationsByJobPostingIdAndStatus(
                                jobPostingId,
                                status
                        )
                        .stream()
                        .map(ApplicationMapper::toResponse)
                        .toList();

        return ResponseEntity.ok(responses);
    }

    @Override
    @PutMapping("/{applicationId}/status")
    @PreAuthorize("@access.managesApplication(#applicationId)")
    public ResponseEntity<ApplicationResponse>
    updateApplicationStatus(
            @PathVariable Long applicationId,
            @Valid @RequestBody
            ApplicationStatusUpdateRequest request
    ) {

        Application application =
                applicationService
                        .updateApplicationStatus(
                                applicationId,
                                request.getStatus()
                        );

        return ResponseEntity.ok(
                ApplicationMapper.toResponse(application)
        );
    }
}
