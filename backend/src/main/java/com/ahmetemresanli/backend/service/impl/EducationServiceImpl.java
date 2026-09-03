package com.ahmetemresanli.backend.service.impl;

import com.ahmetemresanli.backend.entity.CandidateProfile;
import com.ahmetemresanli.backend.entity.Education;
import com.ahmetemresanli.backend.exception.BusinessException;
import com.ahmetemresanli.backend.exception.ResourceNotFoundException;
import com.ahmetemresanli.backend.repository.CandidateProfileRepository;
import com.ahmetemresanli.backend.repository.EducationRepository;
import com.ahmetemresanli.backend.service.IEducationService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EducationServiceImpl
        implements IEducationService {

    private final EducationRepository educationRepository;
    private final CandidateProfileRepository candidateProfileRepository;

    public EducationServiceImpl(
            EducationRepository educationRepository,
            CandidateProfileRepository candidateProfileRepository
    ) {
        this.educationRepository = educationRepository;
        this.candidateProfileRepository = candidateProfileRepository;
    }

    @Override
    public Education createEducation(
            Long candidateProfileId,
            Education education
    ) {

        CandidateProfile candidateProfile =
                candidateProfileRepository
                        .findById(candidateProfileId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Candidate profile not found"
                                )
                        );

        if (education.getUniversityName() == null
                || education.getUniversityName().isBlank()) {

            throw new BusinessException(
                    "University name cannot be empty"
            );
        }

        if (education.getDepartment() == null
                || education.getDepartment().isBlank()) {

            throw new BusinessException(
                    "Department cannot be empty"
            );
        }

        if (education.getEducationLevel() == null) {

            throw new BusinessException(
                    "Education level cannot be null"
            );
        }

        if (education.getStartDate() == null) {

            throw new BusinessException(
                    "Start date cannot be null"
            );
        }

        if (education.getStartDate()
                .isAfter(LocalDate.now())) {

            throw new BusinessException(
                    "Education start date cannot be in the future"
            );
        }

        /*
         * Halen okuyorsa endDate olmamalı.
         */
        if (education.isCurrentlyStudying()
                && education.getEndDate() != null) {

            throw new BusinessException(
                    "End date must be empty for current education"
            );
        }

        /*
         * Halen okumuyorsa endDate girilmeli.
         */
        if (!education.isCurrentlyStudying()
                && education.getEndDate() == null) {

            throw new BusinessException(
                    "End date is required for completed education"
            );
        }

        /*
         * Bitiş tarihi başlangıçtan önce olamaz.
         */
        if (education.getEndDate() != null
                && education.getEndDate()
                .isBefore(education.getStartDate())) {

            throw new BusinessException(
                    "End date cannot be before start date"
            );
        }

        education.setUniversityName(
                education.getUniversityName().trim()
        );

        education.setDepartment(
                education.getDepartment().trim()
        );

        education.setCandidateProfile(
                candidateProfile
        );

        return educationRepository.save(
                education
        );
    }

    @Override
    public Education getEducationById(
            Long id
    ) {

        return educationRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Education not found"
                        )
                );
    }

    @Override
    public List<Education>
    getEducationsByCandidateProfileId(
            Long candidateProfileId
    ) {

        if (!candidateProfileRepository
                .existsById(candidateProfileId)) {

            throw new ResourceNotFoundException(
                    "Candidate profile not found"
            );
        }

        return educationRepository
                .findByCandidateProfileId(
                        candidateProfileId
                );
    }

    @Override
    public void deleteEducation(
            Long id
    ) {

        Education education =
                educationRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Education not found"
                                )
                        );

        educationRepository.delete(
                education
        );
    }
}