package com.ahmetemresanli.backend.repository;

import com.ahmetemresanli.backend.entity.EmploymentVerification;
import com.ahmetemresanli.backend.enums.VerificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmploymentVerificationRepository
        extends JpaRepository<EmploymentVerification, Long> {

    Optional<EmploymentVerification> findByToken(
            String token
    );

    List<EmploymentVerification>
    findByExperienceIdOrderByCreatedAtDesc(
            Long experienceId
    );

    Optional<EmploymentVerification>
    findFirstByExperienceIdAndStatusOrderByCreatedAtDesc(
            Long experienceId,
            VerificationStatus status
    );

    Optional<EmploymentVerification>
    findFirstByExperienceIdOrderByCreatedAtDesc(
            Long experienceId
    );
}