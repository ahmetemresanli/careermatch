package com.ahmetemresanli.backend.repository;

import com.ahmetemresanli.backend.entity.CandidateSkill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CandidateSkillRepository extends JpaRepository<CandidateSkill, Long> {

    List<CandidateSkill> findByCandidateProfileId(
            Long candidateProfileId
    );

    List<CandidateSkill> findBySkillId(Long skillId);

    Optional<CandidateSkill> findByCandidateProfileIdAndSkillId(
            Long candidateProfileId,
            Long skillId
    );

    boolean existsByCandidateProfileIdAndSkillId(
            Long candidateProfileId,
            Long skillId
    );
}