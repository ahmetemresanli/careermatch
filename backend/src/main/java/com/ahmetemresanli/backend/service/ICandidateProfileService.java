package com.ahmetemresanli.backend.service;

import com.ahmetemresanli.backend.entity.CandidateProfile;

import java.util.List;

public interface ICandidateProfileService {

    CandidateProfile createCandidateProfile(
            Long userId,
            CandidateProfile candidateProfile
    );

    CandidateProfile getCandidateProfileById(Long id);

    CandidateProfile getCandidateProfileByUserId(Long userId);

    List<CandidateProfile> getAllCandidateProfiles();
}