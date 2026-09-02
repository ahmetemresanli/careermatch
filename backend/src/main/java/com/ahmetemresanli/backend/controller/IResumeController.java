package com.ahmetemresanli.backend.controller;

import com.ahmetemresanli.backend.dto.request.ResumeCreateRequest;
import com.ahmetemresanli.backend.dto.response.ResumeResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IResumeController {

    ResponseEntity<ResumeResponse> createResume(
            Long candidateProfileId,
            ResumeCreateRequest request
    );

    ResponseEntity<ResumeResponse> getResumeById(
            Long id
    );

    ResponseEntity<List<ResumeResponse>>
    getResumesByCandidateProfileId(
            Long candidateProfileId
    );

    ResponseEntity<ResumeResponse> setDefaultResume(
            Long candidateProfileId,
            Long resumeId
    );
}