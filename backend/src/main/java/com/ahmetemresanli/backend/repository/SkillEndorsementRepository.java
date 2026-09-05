package com.ahmetemresanli.backend.repository;

import com.ahmetemresanli.backend.entity.SkillEndorsement;
import com.ahmetemresanli.backend.enums.SkillEndorsementStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SkillEndorsementRepository
        extends JpaRepository<SkillEndorsement, Long> {

    Optional<SkillEndorsement> findByToken(String token);

    List<SkillEndorsement>
    findByCandidateSkillIdOrderByCreatedAtDesc(
            Long candidateSkillId
    );

    List<SkillEndorsement>
    findByCandidateSkillCandidateProfileIdOrderByCreatedAtDesc(
            Long candidateProfileId
    );

    List<SkillEndorsement>
    findByCandidateSkillCandidateProfileIdAndStatusOrderByCreatedAtDesc(
            Long candidateProfileId,
            SkillEndorsementStatus status
    );

    Optional<SkillEndorsement>
    findByCandidateSkillIdAndEndorserEmailIgnoreCase(
            Long candidateSkillId,
            String endorserEmail
    );

    boolean existsByCandidateSkillIdAndEndorserUserId(Long candidateSkillId, Long endorserUserId);
}
