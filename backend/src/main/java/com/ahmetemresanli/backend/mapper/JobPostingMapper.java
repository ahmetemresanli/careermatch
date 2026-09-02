package com.ahmetemresanli.backend.mapper;

import com.ahmetemresanli.backend.dto.request.JobPostingCreateRequest;
import com.ahmetemresanli.backend.dto.response.JobPostingResponse;
import com.ahmetemresanli.backend.dto.response.JobSkillResponse;
import com.ahmetemresanli.backend.entity.JobPosting;

import java.util.List;

public final class JobPostingMapper {

    private JobPostingMapper() {
    }

    public static JobPosting toEntity(
            JobPostingCreateRequest request
    ) {

        if (request == null) {
            return null;
        }

        JobPosting jobPosting = new JobPosting();

        jobPosting.setTitle(request.getTitle());
        jobPosting.setDescription(request.getDescription());
        jobPosting.setCity(request.getCity());
        jobPosting.setCountry(request.getCountry());

        jobPosting.setEmploymentType(
                request.getEmploymentType()
        );

        jobPosting.setWorkModel(
                request.getWorkModel()
        );

        jobPosting.setJobLevel(
                request.getJobLevel()
        );

        jobPosting.setMinimumExperienceYears(
                request.getMinimumExperienceYears()
        );

        jobPosting.setMinimumSalary(
                request.getMinimumSalary()
        );

        jobPosting.setMaximumSalary(
                request.getMaximumSalary()
        );

        jobPosting.setApplicationDeadline(
                request.getApplicationDeadline()
        );

        return jobPosting;
    }

    public static JobPostingResponse toResponse(
            JobPosting jobPosting
    ) {

        if (jobPosting == null) {
            return null;
        }

        JobPostingResponse response =
                new JobPostingResponse();

        response.setId(jobPosting.getId());

        if (jobPosting.getCompany() != null) {

            response.setCompanyId(
                    jobPosting
                            .getCompany()
                            .getId()
            );

            response.setCompanyName(
                    jobPosting
                            .getCompany()
                            .getName()
            );
        }

        response.setTitle(
                jobPosting.getTitle()
        );

        response.setDescription(
                jobPosting.getDescription()
        );

        response.setCity(
                jobPosting.getCity()
        );

        response.setCountry(
                jobPosting.getCountry()
        );

        response.setEmploymentType(
                jobPosting.getEmploymentType()
        );

        response.setWorkModel(
                jobPosting.getWorkModel()
        );

        response.setJobLevel(
                jobPosting.getJobLevel()
        );

        response.setMinimumExperienceYears(
                jobPosting.getMinimumExperienceYears()
        );

        response.setMinimumSalary(
                jobPosting.getMinimumSalary()
        );

        response.setMaximumSalary(
                jobPosting.getMaximumSalary()
        );

        response.setStatus(
                jobPosting.getStatus()
        );

        response.setApplicationDeadline(
                jobPosting.getApplicationDeadline()
        );

        if (jobPosting.getJobSkills() != null) {

            List<JobSkillResponse> skillResponses =
                    jobPosting
                            .getJobSkills()
                            .stream()
                            .map(JobSkillMapper::toResponse)
                            .toList();

            response.setSkills(skillResponses);
        }

        response.setCreatedAt(
                jobPosting.getCreatedAt()
        );

        response.setUpdatedAt(
                jobPosting.getUpdatedAt()
        );

        return response;
    }
}