package com.ahmetemresanli.backend.controller;

import com.ahmetemresanli.backend.dto.request.JobSkillCreateRequest;
import com.ahmetemresanli.backend.dto.response.JobSkillResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IJobSkillController {

    ResponseEntity<JobSkillResponse> addSkillToJobPosting(
            Long jobPostingId,
            Long skillId,
            JobSkillCreateRequest request
    );

    ResponseEntity<JobSkillResponse> getJobSkillById(
            Long id
    );

    ResponseEntity<List<JobSkillResponse>>
    getSkillsByJobPostingId(
            Long jobPostingId
    );

    ResponseEntity<List<JobSkillResponse>>
    getJobPostingsBySkillId(
            Long skillId
    );
}