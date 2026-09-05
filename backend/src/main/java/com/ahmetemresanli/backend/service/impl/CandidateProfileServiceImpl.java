package com.ahmetemresanli.backend.service.impl;

import com.ahmetemresanli.backend.entity.CandidateProfile;
import com.ahmetemresanli.backend.entity.User;
import com.ahmetemresanli.backend.enums.UserRole;
import com.ahmetemresanli.backend.exception.BusinessException;
import com.ahmetemresanli.backend.exception.DuplicateResourceException;
import com.ahmetemresanli.backend.exception.ResourceNotFoundException;
import com.ahmetemresanli.backend.repository.CandidateProfileRepository;
import com.ahmetemresanli.backend.repository.UserRepository;
import com.ahmetemresanli.backend.service.ICandidateProfileService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CandidateProfileServiceImpl
        implements ICandidateProfileService {

    private final CandidateProfileRepository candidateProfileRepository;
    private final UserRepository userRepository;

    public CandidateProfileServiceImpl(
            CandidateProfileRepository candidateProfileRepository,
            UserRepository userRepository
    ) {
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
                        new ResourceNotFoundException(
                                "User not found"
                        )
                );

        if (user.getRole() != UserRole.CANDIDATE) {
            throw new BusinessException(
                    "Only candidate users can have a candidate profile"
            );
        }

        if (candidateProfileRepository.existsByUserId(userId)) {
            throw new DuplicateResourceException(
                    "Candidate profile already exists"
            );
        }

        if (candidateProfile.getExpectedMinSalary() != null
                && candidateProfile.getExpectedMaxSalary() != null
                && candidateProfile.getExpectedMinSalary()
                .compareTo(candidateProfile.getExpectedMaxSalary()) > 0) {

            throw new BusinessException(
                    "Expected minimum salary cannot be greater than expected maximum salary"
            );
        }

        candidateProfile.setUser(user);

        return candidateProfileRepository.save(candidateProfile);
    }

    @Override
    public CandidateProfile getCandidateProfileById(Long id) {

        return candidateProfileRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Candidate profile not found"
                        )
                );
    }

    @Override
    public CandidateProfile getCandidateProfileByUserId(Long userId) {

        return candidateProfileRepository.findByUserId(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Candidate profile not found"
                        )
                );
    }

    @Override
    public List<CandidateProfile> getAllCandidateProfiles() {
        return candidateProfileRepository.findAll();
    }

    @Override
    public CandidateProfile updateCandidateProfile(Long id, CandidateProfile changes) {
        CandidateProfile current = getCandidateProfileById(id);
        current.setFirstName(changes.getFirstName()); current.setLastName(changes.getLastName());
        current.setAbout(changes.getAbout()); current.setCity(changes.getCity()); current.setCountry(changes.getCountry());
        current.setGithubUrl(changes.getGithubUrl()); current.setLinkedinUrl(changes.getLinkedinUrl()); current.setWebsiteUrl(changes.getWebsiteUrl());
        current.setJobSearchStatus(changes.getJobSearchStatus()); current.setVisibleToRecruiters(changes.isVisibleToRecruiters());
        current.setExpectedMinSalary(changes.getExpectedMinSalary()); current.setExpectedMaxSalary(changes.getExpectedMaxSalary());
        current.setPreferredWorkModel(changes.getPreferredWorkModel());
        if (current.getExpectedMinSalary() != null && current.getExpectedMaxSalary() != null
                && current.getExpectedMinSalary().compareTo(current.getExpectedMaxSalary()) > 0) {
            throw new BusinessException("Expected minimum salary cannot be greater than expected maximum salary");
        }
        return candidateProfileRepository.save(current);
    }
}
