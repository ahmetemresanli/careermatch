package com.ahmetemresanli.backend.repository;

import com.ahmetemresanli.backend.entity.EducationVerification;
import com.ahmetemresanli.backend.enums.VerificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EducationVerificationRepository
        extends JpaRepository<EducationVerification, Long> {

    Optional<EducationVerification> findByToken(
            String token
    );

    List<EducationVerification> findByEducationIdOrderByCreatedAtDesc(
            Long educationId
    );

    Optional<EducationVerification>
    findFirstByEducationIdAndStatusOrderByCreatedAtDesc(
            Long educationId,
            VerificationStatus status
    );

    Optional<EducationVerification>
    findFirstByEducationIdOrderByCreatedAtDesc(
            Long educationId
    );
}