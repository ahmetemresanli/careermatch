package com.ahmetemresanli.backend.repository;

import com.ahmetemresanli.backend.entity.JobSkill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JobSkillRepository extends JpaRepository<JobSkill, Long> {

    List<JobSkill> findByJobPostingId(Long jobPostingId);

    List<JobSkill> findBySkillId(Long skillId);

    Optional<JobSkill> findByJobPostingIdAndSkillId(
            Long jobPostingId,
            Long skillId
    );

    boolean existsByJobPostingIdAndSkillId(
            Long jobPostingId,
            Long skillId
    );
}