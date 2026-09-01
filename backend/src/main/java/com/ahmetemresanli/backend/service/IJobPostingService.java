package com.ahmetemresanli.backend.service;

import com.ahmetemresanli.backend.entity.JobPosting;
import com.ahmetemresanli.backend.enums.EmploymentType;
import com.ahmetemresanli.backend.enums.JobLevel;
import com.ahmetemresanli.backend.enums.WorkModel;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;

public interface IJobPostingService {

    JobPosting createJobPosting(
            Long companyId,
            JobPosting jobPosting
    );

    JobPosting getJobPostingById(Long id);

    List<JobPosting> getJobPostingsByCompanyId(Long companyId);

    List<JobPosting> getAllJobPostings();

    Page<JobPosting> searchJobPostings(
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