package com.ahmetemresanli.backend.controller;

import com.ahmetemresanli.backend.dto.request.ApplicationCreateRequest;
import com.ahmetemresanli.backend.dto.request.ApplicationStatusUpdateRequest;
import com.ahmetemresanli.backend.dto.response.ApplicationResponse;
import com.ahmetemresanli.backend.enums.ApplicationStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IApplicationController {

    ResponseEntity<ApplicationResponse> applyToJob(
            Long candidateProfileId,
            Long jobPostingId,
            ApplicationCreateRequest request
    );

    ResponseEntity<ApplicationResponse> getApplicationById(
            Long id
    );

    ResponseEntity<List<ApplicationResponse>>
    getApplicationsByCandidateProfileId(
            Long candidateProfileId
    );

    ResponseEntity<List<ApplicationResponse>>
    getApplicationsByJobPostingId(
            Long jobPostingId
    );

    ResponseEntity<List<ApplicationResponse>>
    getApplicationsByJobPostingIdAndStatus(
            Long jobPostingId,
            ApplicationStatus status
    );

    ResponseEntity<ApplicationResponse> updateApplicationStatus(
            Long applicationId,
            ApplicationStatusUpdateRequest request
    );
}