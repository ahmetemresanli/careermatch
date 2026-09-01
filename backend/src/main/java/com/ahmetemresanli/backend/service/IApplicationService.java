package com.ahmetemresanli.backend.service;

import com.ahmetemresanli.backend.entity.Application;
import com.ahmetemresanli.backend.enums.ApplicationStatus;

import java.util.List;

public interface IApplicationService {

    Application applyToJob(
            Long candidateProfileId,
            Long jobPostingId,
            Long resumeId,
            String coverLetter
    );

    Application getApplicationById(Long id);

    List<Application> getApplicationsByCandidateProfileId(
            Long candidateProfileId
    );

    List<Application> getApplicationsByJobPostingId(
            Long jobPostingId
    );

    List<Application> getApplicationsByJobPostingIdAndStatus(
            Long jobPostingId,
            ApplicationStatus status
    );

    Application updateApplicationStatus(
            Long applicationId,
            ApplicationStatus newStatus
    );
}