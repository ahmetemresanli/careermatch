package com.ahmetemresanli.backend.service.impl;

import com.ahmetemresanli.backend.entity.Company;
import com.ahmetemresanli.backend.entity.JobPosting;
import com.ahmetemresanli.backend.enums.EmploymentType;
import com.ahmetemresanli.backend.enums.JobLevel;
import com.ahmetemresanli.backend.enums.WorkModel;
import com.ahmetemresanli.backend.exception.BusinessException;
import com.ahmetemresanli.backend.exception.ResourceNotFoundException;
import com.ahmetemresanli.backend.repository.CompanyRepository;
import com.ahmetemresanli.backend.repository.JobPostingRepository;
import com.ahmetemresanli.backend.service.IJobPostingService;
import com.ahmetemresanli.backend.specification.JobPostingSpecification;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
                        new ResourceNotFoundException(
                                "Company not found"
                        )
                );

        if (jobPosting.getTitle() == null
                || jobPosting.getTitle().isBlank()) {

            throw new BusinessException(
                    "Job title cannot be empty"
            );
        }

        if (jobPosting.getDescription() == null
                || jobPosting.getDescription().isBlank()) {

            throw new BusinessException(
                    "Job description cannot be empty"
            );
        }

        if (jobPosting.getEmploymentType() == null) {

            throw new BusinessException(
                    "Employment type cannot be empty"
            );
        }

        if (jobPosting.getWorkModel() == null) {

            throw new BusinessException(
                    "Work model cannot be empty"
            );
        }

        if (jobPosting.getJobLevel() == null) {

            throw new BusinessException(
                    "Job level cannot be empty"
            );
        }

        if (jobPosting.getMinimumExperienceYears() != null
                && jobPosting.getMinimumExperienceYears() < 0) {

            throw new BusinessException(
                    "Minimum experience years cannot be negative"
            );
        }

        if (jobPosting.getMinimumSalary() != null
                && jobPosting.getMinimumSalary().signum() < 0) {

            throw new BusinessException(
                    "Minimum salary cannot be negative"
            );
        }

        if (jobPosting.getMaximumSalary() != null
                && jobPosting.getMaximumSalary().signum() < 0) {

            throw new BusinessException(
                    "Maximum salary cannot be negative"
            );
        }

        if (jobPosting.getMinimumSalary() != null
                && jobPosting.getMaximumSalary() != null
                && jobPosting.getMinimumSalary()
                .compareTo(jobPosting.getMaximumSalary()) > 0) {

            throw new BusinessException(
                    "Minimum salary cannot be greater than maximum salary"
            );
        }

        if (jobPosting.getApplicationDeadline() != null
                && jobPosting.getApplicationDeadline()
                .isBefore(LocalDateTime.now())) {

            throw new BusinessException(
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
                        new ResourceNotFoundException(
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

    @Override
    public Page<JobPosting> searchJobPostings(
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
    ) {

        Sort.Direction direction =
                sortDirection.equalsIgnoreCase("desc")
                        ? Sort.Direction.DESC
                        : Sort.Direction.ASC;

        Sort sort = Sort.by(direction, sortBy);

        Pageable pageable = PageRequest.of(
                page,
                size,
                sort
        );

        return jobPostingRepository.findAll(
                JobPostingSpecification.filter(
                        keyword,
                        city,
                        workModel,
                        employmentType,
                        jobLevel,
                        minimumSalary
                ),
                pageable
        );
    }
}