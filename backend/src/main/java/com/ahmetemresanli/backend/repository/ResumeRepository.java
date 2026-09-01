package com.ahmetemresanli.backend.repository;

import com.ahmetemresanli.backend.entity.Resume;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ResumeRepository
        extends JpaRepository<Resume, Long> {

    List<Resume> findByCandidateProfileId(Long candidateProfileId);

    Optional<Resume> findByCandidateProfileIdAndDefaultResumeTrue(
            Long candidateProfileId
    );
}