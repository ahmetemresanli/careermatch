package com.ahmetemresanli.backend.controller;

import com.ahmetemresanli.backend.entity.CandidateProfile;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ICandidateProfileController {

    ResponseEntity<CandidateProfile> createCandidateProfile(Long userId, CandidateProfile candidateProfile);

    ResponseEntity<CandidateProfile> getCandidateProfileById(Long id);

    ResponseEntity<CandidateProfile> getCandidateProfileByUserId(Long userId);

    ResponseEntity<List<CandidateProfile>> getAllCandidateProfiles();
}
