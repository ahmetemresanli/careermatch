package com.ahmetemresanli.backend.controller.impl;

import com.ahmetemresanli.backend.controller.IJobPostingController;
import com.ahmetemresanli.backend.entity.JobPosting;
import com.ahmetemresanli.backend.service.IJobPostingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/job-postings")
public class JobPostingControllerImpl implements IJobPostingController {

    private final IJobPostingService jobPostingService;

    public JobPostingControllerImpl(
            IJobPostingService jobPostingService
    ) {
        this.jobPostingService = jobPostingService;
    }

    @Override
    @PostMapping("/company/{companyId}")
    public ResponseEntity<JobPosting> createJobPosting(
            @PathVariable Long companyId,
            @RequestBody JobPosting jobPosting
    ) {

        JobPosting savedJobPosting =
                jobPostingService.createJobPosting(
                        companyId,
                        jobPosting
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedJobPosting);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<JobPosting> getJobPostingById(
            @PathVariable Long id
    ) {

        JobPosting jobPosting =
                jobPostingService.getJobPostingById(id);

        return ResponseEntity.ok(jobPosting);
    }

    @Override
    @GetMapping("/company/{companyId}")
    public ResponseEntity<List<JobPosting>>
    getJobPostingsByCompanyId(
            @PathVariable Long companyId
    ) {

        List<JobPosting> jobPostings =
                jobPostingService
                        .getJobPostingsByCompanyId(companyId);

        return ResponseEntity.ok(jobPostings);
    }

    @Override
    @GetMapping
    public ResponseEntity<List<JobPosting>> getAllJobPostings() {

        List<JobPosting> jobPostings =
                jobPostingService.getAllJobPostings();

        return ResponseEntity.ok(jobPostings);
    }
}