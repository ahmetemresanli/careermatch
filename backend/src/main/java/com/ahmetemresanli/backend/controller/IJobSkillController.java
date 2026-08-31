package com.ahmetemresanli.backend.controller;

import com.ahmetemresanli.backend.entity.JobSkill;
import com.ahmetemresanli.backend.enums.SkillLevel;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IJobSkillController {

    ResponseEntity<JobSkill> addSkillToJobPosting(
            Long jobPostingId,
            Long skillId,
            SkillLevel requiredSkillLevel,
            boolean required
    );

    ResponseEntity<JobSkill> getJobSkillById(Long id);

    ResponseEntity<List<JobSkill>> getSkillsByJobPostingId(
            Long jobPostingId
    );

    ResponseEntity<List<JobSkill>> getJobPostingsBySkillId(
            Long skillId
    );
}