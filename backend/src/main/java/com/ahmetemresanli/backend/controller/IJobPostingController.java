package com.ahmetemresanli.backend.controller;

import com.ahmetemresanli.backend.entity.JobPosting;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IJobPostingController {

    ResponseEntity<JobPosting> createJobPosting(
            Long companyId,
            JobPosting jobPosting
    );

    ResponseEntity<JobPosting> getJobPostingById(Long id);

    ResponseEntity<List<JobPosting>> getJobPostingsByCompanyId(
            Long companyId
    );

    ResponseEntity<List<JobPosting>> getAllJobPostings();
}