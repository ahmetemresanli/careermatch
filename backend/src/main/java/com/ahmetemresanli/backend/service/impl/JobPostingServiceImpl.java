package com.ahmetemresanli.backend.service.impl;

import com.ahmetemresanli.backend.entity.Company;
import com.ahmetemresanli.backend.entity.JobPosting;
import com.ahmetemresanli.backend.repository.CompanyRepository;
import com.ahmetemresanli.backend.repository.JobPostingRepository;
import com.ahmetemresanli.backend.service.IJobPostingService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class JobPostingServiceImpl implements IJobPostingService {

    private final JobPostingRepository jobPostingRepository;
    private final CompanyRepository companyRepository;

    public JobPostingServiceImpl(
            JobPostingRepository jobPostingRepository,
            CompanyRepository companyRepository
    ) {
        this.jobPostingRepository = jobPostingRepository;
        this.companyRepository = companyRepository;
    }

    @Override
    public JobPosting createJobPosting(
            Long companyId,
            JobPosting jobPosting
    ) {

        Company company = companyRepository.findById(companyId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Company not found"
                        )
                );

        if (jobPosting.getTitle() == null
                || jobPosting.getTitle().isBlank()) {

            throw new IllegalArgumentException(
                    "Job title cannot be empty"
            );
        }

        if (jobPosting.getDescription() == null
                || jobPosting.getDescription().isBlank()) {

            throw new IllegalArgumentException(
                    "Job description cannot be empty"
            );
        }

        if (jobPosting.getEmploymentType() == null) {
            throw new IllegalArgumentException(
                    "Employment type cannot be empty"
            );
        }

        if (jobPosting.getWorkModel() == null) {
            throw new IllegalArgumentException(
                    "Work model cannot be empty"
            );
        }

        if (jobPosting.getJobLevel() == null) {
            throw new IllegalArgumentException(
                    "Job level cannot be empty"
            );
        }

        if (jobPosting.getMinimumExperienceYears() != null
                && jobPosting.getMinimumExperienceYears() < 0) {

            throw new IllegalArgumentException(
                    "Minimum experience years cannot be negative"
            );
        }

        if (jobPosting.getMinimumSalary() != null
                && jobPosting.getMinimumSalary().signum() < 0) {

            throw new IllegalArgumentException(
                    "Minimum salary cannot be negative"
            );
        }

        if (jobPosting.getMaximumSalary() != null
                && jobPosting.getMaximumSalary().signum() < 0) {

            throw new IllegalArgumentException(
                    "Maximum salary cannot be negative"
            );
        }

        if (jobPosting.getMinimumSalary() != null
                && jobPosting.getMaximumSalary() != null
                && jobPosting.getMinimumSalary()
                .compareTo(jobPosting.getMaximumSalary()) > 0) {

            throw new IllegalArgumentException(
                    "Minimum salary cannot be greater than maximum salary"
            );
        }

        if (jobPosting.getApplicationDeadline() != null
                && jobPosting.getApplicationDeadline()
                .isBefore(LocalDateTime.now())) {

            throw new IllegalArgumentException(
                    "Application deadline cannot be in the past"
            );
        }

        jobPosting.setTitle(
                jobPosting.getTitle().trim()
        );

        jobPosting.setDescription(
                jobPosting.getDescription().trim()
        );

        jobPosting.setCompany(company);

        return jobPostingRepository.save(jobPosting);
    }

    @Override
    public JobPosting getJobPostingById(Long id) {

        return jobPostingRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Job posting not found"
                        )
                );
    }

    @Override
    public List<JobPosting> getJobPostingsByCompanyId(
            Long companyId
    ) {

        return jobPostingRepository
                .findByCompanyId(companyId);
    }

    @Override
    public List<JobPosting> getAllJobPostings() {

        return jobPostingRepository.findAll();
    }
}