package com.ahmetemresanli.backend.controller;

import com.ahmetemresanli.backend.dto.request.ExperienceCreateRequest;
import com.ahmetemresanli.backend.dto.response.ExperienceResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IExperienceController {

    ResponseEntity<ExperienceResponse> createExperience(
            Long candidateProfileId,
            ExperienceCreateRequest request
    );

    ResponseEntity<ExperienceResponse> getExperienceById(
            Long id
    );

    ResponseEntity<List<ExperienceResponse>>
    getExperiencesByCandidate(
            Long candidateProfileId
    );

    ResponseEntity<Void> deleteExperience(
            Long id
    );
}