package com.ahmetemresanli.backend.service.impl;

import com.ahmetemresanli.backend.entity.CandidateProfile;
import com.ahmetemresanli.backend.entity.User;
import com.ahmetemresanli.backend.enums.UserRole;
import com.ahmetemresanli.backend.repository.CandidateProfileRepository;
import com.ahmetemresanli.backend.repository.UserRepository;
import com.ahmetemresanli.backend.service.ICandidateProfileService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CandidateProfileServiceImpl implements ICandidateProfileService {

    private final CandidateProfileRepository candidateProfileRepository;
    private final UserRepository userRepository;

    public CandidateProfileServiceImpl(CandidateProfileRepository candidateProfileRepository,
                                       UserRepository userRepository){
        this.candidateProfileRepository = candidateProfileRepository;
        this.userRepository = userRepository;
    }

    @Override
    public CandidateProfile createCandidateProfile(
            Long userId,
            CandidateProfile candidateProfile
    ) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new IllegalArgumentException("User not found")
                );

        if (user.getRole() != UserRole.CANDIDATE) {
            throw new IllegalArgumentException(
                    "Only candidate users can have a candidate profile"
            );
        }

        if (candidateProfileRepository.existsByUserId(userId)) {
            throw new IllegalArgumentException(
                    "Candidate profile already exists"
            );
        }

        candidateProfile.setUser(user);

        return candidateProfileRepository.save(candidateProfile);
    }

    @Override
    public CandidateProfile getCandidateProfileById(Long id) {
        return candidateProfileRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException(
                    "Candidate profile not found"));
    }

    @Override
    public CandidateProfile getCandidateProfileByUserId(Long userId) {
        return candidateProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Candidate profile not found"
                ));
    }

    @Override
    public List<CandidateProfile> getAllCandidateProfiles() {
        return candidateProfileRepository.findAll();
    }
}
