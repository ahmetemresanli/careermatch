package com.ahmetemresanli.backend.controller.impl;

import com.ahmetemresanli.backend.controller.IJobSkillController;
import com.ahmetemresanli.backend.dto.request.JobSkillCreateRequest;
import com.ahmetemresanli.backend.dto.response.JobSkillResponse;
import com.ahmetemresanli.backend.entity.JobSkill;
import com.ahmetemresanli.backend.mapper.JobSkillMapper;
import com.ahmetemresanli.backend.service.IJobSkillService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/job-skills")
public class JobSkillControllerImpl
        implements IJobSkillController {

    private final IJobSkillService jobSkillService;

    public JobSkillControllerImpl(
            IJobSkillService jobSkillService
    ) {
        this.jobSkillService = jobSkillService;
    }

    @Override
    @PostMapping("/job/{jobPostingId}/skill/{skillId}")
    public ResponseEntity<JobSkillResponse>
    addSkillToJobPosting(
            @PathVariable Long jobPostingId,
            @PathVariable Long skillId,
            @Valid @RequestBody JobSkillCreateRequest request
    ) {

        boolean required =
                request.getRequired() == null
                        || request.getRequired();

        JobSkill jobSkill =
                jobSkillService.addSkillToJobPosting(
                        jobPostingId,
                        skillId,
                        request.getRequiredSkillLevel(),
                        required
                );

        JobSkillResponse response =
                JobSkillMapper.toResponse(jobSkill);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<JobSkillResponse>
    getJobSkillById(
            @PathVariable Long id
    ) {

        JobSkill jobSkill =
                jobSkillService.getJobSkillById(id);

        return ResponseEntity.ok(
                JobSkillMapper.toResponse(jobSkill)
        );
    }

    @Override
    @GetMapping("/job/{jobPostingId}")
    public ResponseEntity<List<JobSkillResponse>>
    getSkillsByJobPostingId(
            @PathVariable Long jobPostingId
    ) {

        List<JobSkillResponse> responses =
                jobSkillService
                        .getSkillsByJobPostingId(jobPostingId)
                        .stream()
                        .map(JobSkillMapper::toResponse)
                        .toList();

        return ResponseEntity.ok(responses);
    }

    @Override
    @GetMapping("/skill/{skillId}")
    public ResponseEntity<List<JobSkillResponse>>
    getJobPostingsBySkillId(
            @PathVariable Long skillId
    ) {

        List<JobSkillResponse> responses =
                jobSkillService
                        .getJobPostingsBySkillId(skillId)
                        .stream()
                        .map(JobSkillMapper::toResponse)
                        .toList();

        return ResponseEntity.ok(responses);
    }
}