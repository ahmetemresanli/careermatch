package com.ahmetemresanli.backend.repository;

import com.ahmetemresanli.backend.entity.Reference;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReferenceRepository
        extends JpaRepository<Reference, Long> {

    Optional<Reference> findByReferenceRequestId(
            Long referenceRequestId
    );

    boolean existsByReferenceRequestId(
            Long referenceRequestId
    );

    List<Reference>
    findByCandidateProfileIdOrderByCreatedAtDesc(
            Long candidateProfileId
    );

    List<Reference>
    findByCandidateProfileIdAndVisibleTrueOrderByCreatedAtDesc(
            Long candidateProfileId
    );
}