package com.ahmetemresanli.backend.controller;

import com.ahmetemresanli.backend.dto.request.CandidateProfileCreateRequest;
import com.ahmetemresanli.backend.dto.response.CandidateProfileResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ICandidateProfileController {

    ResponseEntity<CandidateProfileResponse> createCandidateProfile(
            Long userId,
            CandidateProfileCreateRequest request
    );

    ResponseEntity<CandidateProfileResponse> getCandidateProfileById(
            Long id
    );

    ResponseEntity<CandidateProfileResponse> getCandidateProfileByUserId(
            Long userId
    );

    ResponseEntity<List<CandidateProfileResponse>>
    getAllCandidateProfiles();
}