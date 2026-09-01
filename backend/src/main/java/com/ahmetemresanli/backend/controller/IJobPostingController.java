package com.ahmetemresanli.backend.controller;

import com.ahmetemresanli.backend.entity.JobPosting;
import com.ahmetemresanli.backend.enums.EmploymentType;
import com.ahmetemresanli.backend.enums.JobLevel;
import com.ahmetemresanli.backend.enums.WorkModel;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
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

    ResponseEntity<Page<JobPosting>> searchJobPostings(
            String keyword,
            String city,
            WorkModel workModel,
            EmploymentType employmentType,
            JobLevel jobLevel,
            BigDecimal minimumSalary,
            int page,
            int size,
            String sortBy,
            String sortDirection
    );


}