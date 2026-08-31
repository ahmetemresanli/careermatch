package com.ahmetemresanli.backend.service.impl;

import com.ahmetemresanli.backend.entity.JobPosting;
import com.ahmetemresanli.backend.entity.JobSkill;
import com.ahmetemresanli.backend.entity.Skill;
import com.ahmetemresanli.backend.enums.SkillLevel;
import com.ahmetemresanli.backend.repository.JobPostingRepository;
import com.ahmetemresanli.backend.repository.JobSkillRepository;
import com.ahmetemresanli.backend.repository.SkillRepository;
import com.ahmetemresanli.backend.service.IJobSkillService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobSkillServiceImpl implements IJobSkillService {

    private final JobSkillRepository jobSkillRepository;
    private final JobPostingRepository jobPostingRepository;
    private final SkillRepository skillRepository;

    public JobSkillServiceImpl(
            JobSkillRepository jobSkillRepository,
            JobPostingRepository jobPostingRepository,
            SkillRepository skillRepository
    ) {
        this.jobSkillRepository = jobSkillRepository;
        this.jobPostingRepository = jobPostingRepository;
        this.skillRepository = skillRepository;
    }

    @Override
    public JobSkill addSkillToJobPosting(
            Long jobPostingId,
            Long skillId,
            SkillLevel requiredSkillLevel,
            boolean required
    ) {

        JobPosting jobPosting =
                jobPostingRepository.findById(jobPostingId)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Job posting not found"
                                )
                        );

        Skill skill = skillRepository.findById(skillId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Skill not found"
                        )
                );

        if (jobSkillRepository
                .existsByJobPostingIdAndSkillId(
                        jobPostingId,
                        skillId
                )) {

            throw new IllegalArgumentException(
                    "Job posting already has this skill"
            );
        }

        if (requiredSkillLevel == null) {
            throw new IllegalArgumentException(
                    "Required skill level cannot be empty"
            );
        }

        JobSkill jobSkill = new JobSkill();

        jobSkill.setJobPosting(jobPosting);
        jobSkill.setSkill(skill);
        jobSkill.setRequiredSkillLevel(requiredSkillLevel);
        jobSkill.setRequired(required);

        return jobSkillRepository.save(jobSkill);
    }

    @Override
    public JobSkill getJobSkillById(Long id) {

        return jobSkillRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Job skill not found"
                        )
                );
    }

    @Override
    public List<JobSkill> getSkillsByJobPostingId(
            Long jobPostingId
    ) {

        return jobSkillRepository
                .findByJobPostingId(jobPostingId);
    }

    @Override
    public List<JobSkill> getJobPostingsBySkillId(
            Long skillId
    ) {

        return jobSkillRepository
                .findBySkillId(skillId);
    }
}