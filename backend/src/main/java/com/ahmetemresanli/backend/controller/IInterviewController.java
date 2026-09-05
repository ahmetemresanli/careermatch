package com.ahmetemresanli.backend.controller;

import com.ahmetemresanli.backend.dto.request.InterviewCompleteRequest;
import com.ahmetemresanli.backend.dto.request.InterviewCreateRequest;
import com.ahmetemresanli.backend.dto.response.InterviewResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IInterviewController {

    ResponseEntity<InterviewResponse> createInterview(
            Long applicationId,
            InterviewCreateRequest request
    );

    ResponseEntity<InterviewResponse> completeInterview(
            Long interviewId,
            InterviewCompleteRequest request
    );

    ResponseEntity<InterviewResponse> cancelInterview(
            Long interviewId
    );

    ResponseEntity<InterviewResponse> getInterviewById(
            Long interviewId
    );

    ResponseEntity<List<InterviewResponse>>
    getInterviewsByApplication(
            Long applicationId
    );

    ResponseEntity<List<InterviewResponse>>
    getInterviewsByCandidate(
            Long candidateProfileId
    );

    ResponseEntity<List<InterviewResponse>>
    getInterviewsByCompany(
            Long companyId
    );
}