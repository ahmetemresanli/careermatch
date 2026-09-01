package com.ahmetemresanli.backend.repository;

import com.ahmetemresanli.backend.entity.Application;
import com.ahmetemresanli.backend.enums.ApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ApplicationRepository
        extends JpaRepository<Application, Long> {

    List<Application> findByCandidateProfileId(
            Long candidateProfileId
    );

    List<Application> findByJobPostingId(
            Long jobPostingId
    );

    List<Application> findByJobPostingIdAndStatus(
            Long jobPostingId,
            ApplicationStatus status
    );

    Optional<Application> findByCandidateProfileIdAndJobPostingId(
            Long candidateProfileId,
            Long jobPostingId
    );

    boolean existsByCandidateProfileIdAndJobPostingId(
            Long candidateProfileId,
            Long jobPostingId
    );
}