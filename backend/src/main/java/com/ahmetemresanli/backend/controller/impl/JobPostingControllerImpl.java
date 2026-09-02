package com.ahmetemresanli.backend.controller.impl;

import com.ahmetemresanli.backend.controller.IJobPostingController;
import com.ahmetemresanli.backend.dto.request.JobPostingCreateRequest;
import com.ahmetemresanli.backend.dto.request.JobPostingStatusUpdateRequest;
import com.ahmetemresanli.backend.dto.response.JobPostingResponse;
import com.ahmetemresanli.backend.entity.JobPosting;
import com.ahmetemresanli.backend.enums.EmploymentType;
import com.ahmetemresanli.backend.enums.JobLevel;
import com.ahmetemresanli.backend.enums.WorkModel;
import com.ahmetemresanli.backend.mapper.JobPostingMapper;
import com.ahmetemresanli.backend.service.IJobPostingService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/job-postings")
public class JobPostingControllerImpl
        implements IJobPostingController {

    private final IJobPostingService jobPostingService;

    public JobPostingControllerImpl(
            IJobPostingService jobPostingService
    ) {
        this.jobPostingService = jobPostingService;
    }

    @Override
    @PostMapping("/company/{companyId}")
    public ResponseEntity<JobPostingResponse> createJobPosting(
            @PathVariable Long companyId,
            @Valid @RequestBody JobPostingCreateRequest request
    ) {

        JobPosting jobPosting =
                JobPostingMapper.toEntity(request);

        JobPosting createdJobPosting =
                jobPostingService.createJobPosting(
                        companyId,
                        jobPosting
                );

        JobPostingResponse response =
                JobPostingMapper.toResponse(
                        createdJobPosting
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<JobPostingResponse>
    getJobPostingById(
            @PathVariable Long id
    ) {

        JobPosting jobPosting =
                jobPostingService.getJobPostingById(id);

        return ResponseEntity.ok(
                JobPostingMapper.toResponse(jobPosting)
        );
    }

    @Override
    @GetMapping("/company/{companyId}")
    public ResponseEntity<List<JobPostingResponse>>
    getJobPostingsByCompanyId(
            @PathVariable Long companyId
    ) {

        List<JobPostingResponse> responses =
                jobPostingService
                        .getJobPostingsByCompanyId(companyId)
                        .stream()
                        .map(JobPostingMapper::toResponse)
                        .toList();

        return ResponseEntity.ok(responses);
    }

    @Override
    @GetMapping
    public ResponseEntity<List<JobPostingResponse>>
    getAllJobPostings() {

        List<JobPostingResponse> responses =
                jobPostingService
                        .getAllJobPostings()
                        .stream()
                        .map(JobPostingMapper::toResponse)
                        .toList();

        return ResponseEntity.ok(responses);
    }

    @Override
    @GetMapping("/search")
    public ResponseEntity<Page<JobPostingResponse>>
    searchJobPostings(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) WorkModel workModel,
            @RequestParam(required = false) EmploymentType employmentType,
            @RequestParam(required = false) JobLevel jobLevel,
            @RequestParam(required = false) BigDecimal minimumSalary,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection
    ) {

        Page<JobPosting> jobPostings =
                jobPostingService.searchJobPostings(
                        keyword,
                        city,
                        workModel,
                        employmentType,
                        jobLevel,
                        minimumSalary,
                        page,
                        size,
                        sortBy,
                        sortDirection
                );

        Page<JobPostingResponse> responses =
                jobPostings.map(
                        JobPostingMapper::toResponse
                );

        return ResponseEntity.ok(responses);
    }

    @PutMapping("/{jobPostingId}/status")
    @Override
    public ResponseEntity<JobPostingResponse> updateStatus(
            @PathVariable Long jobPostingId,
            @Valid @RequestBody JobPostingStatusUpdateRequest request
    ) {

        JobPosting updatedJobPosting =
                jobPostingService.updateStatus(
                        jobPostingId,
                        request.getStatus()
                );

        return ResponseEntity.ok(
                JobPostingMapper.toResponse(updatedJobPosting)
        );
    }
}