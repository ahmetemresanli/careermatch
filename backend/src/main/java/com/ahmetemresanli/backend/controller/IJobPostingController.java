package com.ahmetemresanli.backend.controller;

import com.ahmetemresanli.backend.dto.request.JobPostingCreateRequest;
import com.ahmetemresanli.backend.dto.request.JobPostingStatusUpdateRequest;
import com.ahmetemresanli.backend.dto.response.JobPostingResponse;
import com.ahmetemresanli.backend.enums.EmploymentType;
import com.ahmetemresanli.backend.enums.JobLevel;
import com.ahmetemresanli.backend.enums.WorkModel;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

public interface IJobPostingController {

    ResponseEntity<JobPostingResponse> createJobPosting(
            Long companyId,
            JobPostingCreateRequest request
    );

    ResponseEntity<JobPostingResponse> getJobPostingById(
            Long id
    );

    ResponseEntity<List<JobPostingResponse>>
    getJobPostingsByCompanyId(
            Long companyId
    );

    ResponseEntity<List<JobPostingResponse>>
    getAllJobPostings();

    ResponseEntity<Page<JobPostingResponse>> searchJobPostings(
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

    ResponseEntity<JobPostingResponse> updateStatus(
            Long jobPostingId,
            JobPostingStatusUpdateRequest request
    );
}