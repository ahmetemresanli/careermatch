package com.ahmetemresanli.backend.service;

import com.ahmetemresanli.backend.entity.JobSkill;
import com.ahmetemresanli.backend.enums.SkillLevel;

import java.util.List;

public interface IJobSkillService {

    JobSkill addSkillToJobPosting(
            Long jobPostingId,
            Long skillId,
            SkillLevel requiredSkillLevel,
            boolean required
    );

    JobSkill getJobSkillById(Long id);

    List<JobSkill> getSkillsByJobPostingId(
            Long jobPostingId
    );

    List<JobSkill> getJobPostingsBySkillId(
            Long skillId
    );
}