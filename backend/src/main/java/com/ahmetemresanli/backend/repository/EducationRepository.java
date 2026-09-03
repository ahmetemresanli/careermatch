package com.ahmetemresanli.backend.repository;

import com.ahmetemresanli.backend.entity.Education;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EducationRepository
        extends JpaRepository<Education, Long> {

    List<Education> findByCandidateProfileId(
            Long candidateProfileId
    );
}