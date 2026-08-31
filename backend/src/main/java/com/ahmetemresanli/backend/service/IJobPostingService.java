package com.ahmetemresanli.backend.service;

import com.ahmetemresanli.backend.entity.JobPosting;

import java.util.List;

public interface IJobPostingService {

    JobPosting createJobPosting(
            Long companyId,
            JobPosting jobPosting
    );

    JobPosting getJobPostingById(Long id);

    List<JobPosting> getJobPostingsByCompanyId(Long companyId);

    List<JobPosting> getAllJobPostings();
}