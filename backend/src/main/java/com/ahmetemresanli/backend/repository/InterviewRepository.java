package com.ahmetemresanli.backend.repository;

import com.ahmetemresanli.backend.entity.Interview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InterviewRepository
        extends JpaRepository<Interview, Long> {

    List<Interview> findByApplicationIdOrderByScheduledAtAsc(
            Long applicationId
    );

    List<Interview>
    findByApplicationCandidateProfileIdOrderByScheduledAtDesc(
            Long candidateProfileId
    );

    List<Interview>
    findByApplicationJobPostingCompanyIdOrderByScheduledAtDesc(
            Long companyId
    );
}