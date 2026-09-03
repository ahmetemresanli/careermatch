package com.ahmetemresanli.backend.service;

import com.ahmetemresanli.backend.entity.Education;

import java.util.List;

public interface IEducationService {

    Education createEducation(
            Long candidateProfileId,
            Education education
    );

    Education getEducationById(
            Long id
    );

    List<Education> getEducationsByCandidateProfileId(
            Long candidateProfileId
    );

    void deleteEducation(
            Long id
    );
}