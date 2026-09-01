package com.ahmetemresanli.backend.controller;

import com.ahmetemresanli.backend.entity.Application;
import com.ahmetemresanli.backend.enums.ApplicationStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IApplicationController {

    ResponseEntity<Application> applyToJob(
            Long candidateProfileId,
            Long jobPostingId,
            Long resumeId,
            String coverLetter
    );

    ResponseEntity<Application> getApplicationById(
            Long id
    );

    ResponseEntity<List<Application>>
    getApplicationsByCandidateProfileId(
            Long candidateProfileId
    );

    ResponseEntity<List<Application>>
    getApplicationsByJobPostingId(
            Long jobPostingId
    );

    ResponseEntity<List<Application>>
    getApplicationsByJobPostingIdAndStatus(
            Long jobPostingId,
            ApplicationStatus status
    );

    ResponseEntity<Application> updateApplicationStatus(
            Long applicationId,
            ApplicationStatus status
    );
}