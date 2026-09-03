package com.ahmetemresanli.backend.controller;

import com.ahmetemresanli.backend.dto.request.EducationCreateRequest;
import com.ahmetemresanli.backend.dto.response.EducationResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IEducationController {

    ResponseEntity<EducationResponse> createEducation(
            Long candidateProfileId,
            EducationCreateRequest request
    );

    ResponseEntity<EducationResponse> getEducationById(
            Long id
    );

    ResponseEntity<List<EducationResponse>>
    getEducationsByCandidate(
            Long candidateProfileId
    );

    ResponseEntity<Void> deleteEducation(
            Long id
    );
}