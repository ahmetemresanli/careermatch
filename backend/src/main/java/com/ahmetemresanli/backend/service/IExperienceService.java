package com.ahmetemresanli.backend.service;

import com.ahmetemresanli.backend.entity.Experience;

import java.util.List;

public interface IExperienceService {

    Experience createExperience(
            Long candidateProfileId,
            Experience experience
    );

    Experience getExperienceById(
            Long id
    );

    List<Experience> getExperiencesByCandidateProfileId(
            Long candidateProfileId
    );

    void deleteExperience(
            Long id
    );
}