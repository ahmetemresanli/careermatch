package com.ahmetemresanli.backend.mapper;

import com.ahmetemresanli.backend.dto.request.JobSkillCreateRequest;
import com.ahmetemresanli.backend.dto.response.JobSkillResponse;
import com.ahmetemresanli.backend.entity.JobSkill;

public final class JobSkillMapper {

    private JobSkillMapper() {
    }

    public static JobSkill toEntity(JobSkillCreateRequest request) {

        if (request == null) {
            return null;
        }

        JobSkill jobSkill = new JobSkill();

        jobSkill.setRequiredSkillLevel(
                request.getRequiredSkillLevel()
        );

        if (request.getRequired() != null) {
            jobSkill.setRequired(
                    request.getRequired()
            );
        }

        return jobSkill;
    }

    public static JobSkillResponse toResponse(JobSkill jobSkill) {

        if (jobSkill == null) {
            return null;
        }

        JobSkillResponse response =
                new JobSkillResponse();

        response.setId(jobSkill.getId());

        if (jobSkill.getJobPosting() != null) {
            response.setJobPostingId(
                    jobSkill
                            .getJobPosting()
                            .getId()
            );
        }

        if (jobSkill.getSkill() != null) {

            response.setSkillId(
                    jobSkill
                            .getSkill()
                            .getId()
            );

            response.setSkillName(
                    jobSkill
                            .getSkill()
                            .getName()
            );
        }

        response.setRequiredSkillLevel(
                jobSkill.getRequiredSkillLevel()
        );

        response.setRequired(
                jobSkill.isRequired()
        );

        response.setCreatedAt(
                jobSkill.getCreatedAt()
        );

        response.setUpdatedAt(
                jobSkill.getUpdatedAt()
        );

        return response;
    }
}