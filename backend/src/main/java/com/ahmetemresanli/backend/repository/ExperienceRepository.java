package com.ahmetemresanli.backend.repository;

import com.ahmetemresanli.backend.entity.Experience;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExperienceRepository
        extends JpaRepository<Experience, Long> {

    List<Experience> findByCandidateProfileId(
            Long candidateProfileId
    );
}