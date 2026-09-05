package com.ahmetemresanli.backend.repository;

import com.ahmetemresanli.backend.entity.ReferenceRequest;
import com.ahmetemresanli.backend.enums.ReferenceRequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReferenceRequestRepository
        extends JpaRepository<ReferenceRequest, Long> {

    Optional<ReferenceRequest> findByToken(
            String token
    );

    List<ReferenceRequest>
    findByCandidateProfileIdOrderByCreatedAtDesc(
            Long candidateProfileId
    );

    Optional<ReferenceRequest>
    findFirstByCandidateProfileIdAndReferenceEmailIgnoreCaseAndStatusOrderByCreatedAtDesc(
            Long candidateProfileId,
            String referenceEmail,
            ReferenceRequestStatus status
    );
}