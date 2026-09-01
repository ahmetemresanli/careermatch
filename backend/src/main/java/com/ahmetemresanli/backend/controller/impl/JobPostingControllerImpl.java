package com.ahmetemresanli.backend.controller.impl;

import com.ahmetemresanli.backend.controller.IJobPostingController;
import com.ahmetemresanli.backend.entity.JobPosting;
import com.ahmetemresanli.backend.enums.EmploymentType;
import com.ahmetemresanli.backend.enums.JobLevel;
import com.ahmetemresanli.backend.enums.WorkModel;
import com.ahmetemresanli.backend.service.IJobPostingService;
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
    public ResponseEntity<List<JobPosting>>
    getAllJobPostings() {

        List<JobPosting> jobPostings =
                jobPostingService.getAllJobPostings();

        return ResponseEntity.ok(jobPostings);
    }

    @Override
    @GetMapping("/search")
    public ResponseEntity<Page<JobPosting>> searchJobPostings(
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

        return ResponseEntity.ok(jobPostings);
    }
}