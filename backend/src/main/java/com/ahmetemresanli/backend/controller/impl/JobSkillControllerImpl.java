package com.ahmetemresanli.backend.controller.impl;

import com.ahmetemresanli.backend.controller.IJobSkillController;
import com.ahmetemresanli.backend.entity.JobSkill;
import com.ahmetemresanli.backend.enums.SkillLevel;
import com.ahmetemresanli.backend.service.IJobSkillService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/job-skills")
public class JobSkillControllerImpl implements IJobSkillController {

    private final IJobSkillService jobSkillService;

    public JobSkillControllerImpl(IJobSkillService jobSkillService) {
        this.jobSkillService = jobSkillService;
    }

    @Override
    @PostMapping("/job/{jobPostingId}/skill/{skillId}")
    public ResponseEntity<JobSkill> addSkillToJobPosting(
            @PathVariable Long jobPostingId,
            @PathVariable Long skillId,
            @RequestParam SkillLevel requiredSkillLevel,
            @RequestParam(defaultValue = "true") boolean required
    ) {

        JobSkill jobSkill =
                jobSkillService.addSkillToJobPosting(
                        jobPostingId,
                        skillId,
                        requiredSkillLevel,
                        required
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(jobSkill);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<JobSkill> getJobSkillById(
            @PathVariable Long id
    ) {

        JobSkill jobSkill =
                jobSkillService.getJobSkillById(id);

        return ResponseEntity.ok(jobSkill);
    }

    @Override
    @GetMapping("/job/{jobPostingId}")
    public ResponseEntity<List<JobSkill>>
    getSkillsByJobPostingId(
            @PathVariable Long jobPostingId
    ) {

        List<JobSkill> jobSkills =
                jobSkillService
                        .getSkillsByJobPostingId(jobPostingId);

        return ResponseEntity.ok(jobSkills);
    }

    @Override
    @GetMapping("/skill/{skillId}")
    public ResponseEntity<List<JobSkill>>
    getJobPostingsBySkillId(
            @PathVariable Long skillId
    ) {

        List<JobSkill> jobSkills =
                jobSkillService
                        .getJobPostingsBySkillId(skillId);

        return ResponseEntity.ok(jobSkills);
    }
}