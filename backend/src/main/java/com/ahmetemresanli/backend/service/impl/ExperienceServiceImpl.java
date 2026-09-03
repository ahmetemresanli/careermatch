package com.ahmetemresanli.backend.service.impl;

import com.ahmetemresanli.backend.entity.CandidateProfile;
import com.ahmetemresanli.backend.entity.Experience;
import com.ahmetemresanli.backend.exception.BusinessException;
import com.ahmetemresanli.backend.exception.ResourceNotFoundException;
import com.ahmetemresanli.backend.repository.CandidateProfileRepository;
import com.ahmetemresanli.backend.repository.ExperienceRepository;
import com.ahmetemresanli.backend.service.IExperienceService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ExperienceServiceImpl
        implements IExperienceService {

    private final ExperienceRepository experienceRepository;
    private final CandidateProfileRepository candidateProfileRepository;

    public ExperienceServiceImpl(
            ExperienceRepository experienceRepository,
            CandidateProfileRepository candidateProfileRepository
    ) {
        this.experienceRepository = experienceRepository;
        this.candidateProfileRepository = candidateProfileRepository;
    }

    @Override
    public Experience createExperience(
            Long candidateProfileId,
            Experience experience
    ) {

        CandidateProfile candidateProfile =
                candidateProfileRepository
                        .findById(candidateProfileId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Candidate profile not found"
                                )
                        );

        if (experience.getCompanyName() == null
                || experience.getCompanyName().isBlank()) {

            throw new BusinessException(
                    "Company name cannot be empty"
            );
        }

        if (experience.getPositionTitle() == null
                || experience.getPositionTitle().isBlank()) {

            throw new BusinessException(
                    "Position title cannot be empty"
            );
        }

        if (experience.getStartDate() == null) {

            throw new BusinessException(
                    "Start date cannot be null"
            );
        }

        if (experience.getStartDate()
                .isAfter(LocalDate.now())) {

            throw new BusinessException(
                    "Experience start date cannot be in the future"
            );
        }

        /*
         * Halen çalışıyorsa endDate olmamalı.
         */
        if (experience.isCurrentlyWorking()
                && experience.getEndDate() != null) {

            throw new BusinessException(
                    "End date must be empty for current employment"
            );
        }

        /*
         * Artık çalışmıyorsa endDate zorunlu.
         */
        if (!experience.isCurrentlyWorking()
                && experience.getEndDate() == null) {

            throw new BusinessException(
                    "End date is required for completed employment"
            );
        }

        /*
         * End date başlangıç tarihinden önce olamaz.
         */
        if (experience.getEndDate() != null
                && experience.getEndDate()
                .isBefore(experience.getStartDate())) {

            throw new BusinessException(
                    "End date cannot be before start date"
            );
        }

        /*
         * Geçmiş iş deneyiminin bitiş tarihi
         * gelecekte olamaz.
         */
        if (experience.getEndDate() != null
                && experience.getEndDate()
                .isAfter(LocalDate.now())) {

            throw new BusinessException(
                    "Experience end date cannot be in the future"
            );
        }

        experience.setCompanyName(
                experience.getCompanyName().trim()
        );

        experience.setPositionTitle(
                experience.getPositionTitle().trim()
        );

        experience.setCandidateProfile(
                candidateProfile
        );

        return experienceRepository.save(
                experience
        );
    }

    @Override
    public Experience getExperienceById(
            Long id
    ) {

        return experienceRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Experience not found"
                        )
                );
    }

    @Override
    public List<Experience>
    getExperiencesByCandidateProfileId(
            Long candidateProfileId
    ) {

        if (!candidateProfileRepository
                .existsById(candidateProfileId)) {

            throw new ResourceNotFoundException(
                    "Candidate profile not found"
            );
        }

        return experienceRepository
                .findByCandidateProfileId(
                        candidateProfileId
                );
    }

    @Override
    public void deleteExperience(
            Long id
    ) {

        Experience experience =
                experienceRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Experience not found"
                                )
                        );

        experienceRepository.delete(
                experience
        );
    }
}