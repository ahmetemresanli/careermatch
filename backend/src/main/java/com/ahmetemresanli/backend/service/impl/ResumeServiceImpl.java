package com.ahmetemresanli.backend.service.impl;

import com.ahmetemresanli.backend.entity.CandidateProfile;
import com.ahmetemresanli.backend.entity.Resume;
import com.ahmetemresanli.backend.exception.BusinessException;
import com.ahmetemresanli.backend.exception.ResourceNotFoundException;
import com.ahmetemresanli.backend.repository.CandidateProfileRepository;
import com.ahmetemresanli.backend.repository.ResumeRepository;
import com.ahmetemresanli.backend.service.IResumeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ResumeServiceImpl implements IResumeService {

    private final ResumeRepository resumeRepository;
    private final CandidateProfileRepository candidateProfileRepository;

    public ResumeServiceImpl(
            ResumeRepository resumeRepository,
            CandidateProfileRepository candidateProfileRepository
    ) {
        this.resumeRepository = resumeRepository;
        this.candidateProfileRepository = candidateProfileRepository;
    }

    @Override
    @Transactional
    public Resume createResume(
            Long candidateProfileId,
            Resume resume
    ) {

        CandidateProfile candidateProfile =
                candidateProfileRepository.findById(candidateProfileId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Candidate profile not found"
                                )
                        );

        if (resume.getFileName() == null
                || resume.getFileName().isBlank()) {

            throw new BusinessException(
                    "Resume file name cannot be empty"
            );
        }

        if (resume.getFileUrl() == null
                || resume.getFileUrl().isBlank()) {

            throw new BusinessException(
                    "Resume file URL cannot be empty"
            );
        }

        resume.setCandidateProfile(candidateProfile);

        List<Resume> candidateResumes =
                resumeRepository.findByCandidateProfileId(
                        candidateProfileId
                );

        // İlk CV otomatik olarak default olur
        if (candidateResumes.isEmpty()) {

            resume.setDefaultResume(true);

        }
        // Yeni CV default olarak oluşturuluyorsa
        // eski default CV devre dışı bırakılır
        else if (resume.isDefaultResume()) {

            candidateResumes.stream()
                    .filter(Resume::isDefaultResume)
                    .forEach(existingResume -> {
                        existingResume.setDefaultResume(false);
                        resumeRepository.save(existingResume);
                    });
        }

        return resumeRepository.save(resume);
    }

    @Override
    public Resume getResumeById(Long id) {

        return resumeRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Resume not found"
                        )
                );
    }

    @Override
    public List<Resume> getResumesByCandidateProfileId(
            Long candidateProfileId
    ) {

        if (!candidateProfileRepository.existsById(candidateProfileId)) {

            throw new ResourceNotFoundException(
                    "Candidate profile not found"
            );
        }

        return resumeRepository.findByCandidateProfileId(
                candidateProfileId
        );
    }

    @Override
    @Transactional
    public Resume setDefaultResume(
            Long candidateProfileId,
            Long resumeId
    ) {

        CandidateProfile candidateProfile =
                candidateProfileRepository.findById(candidateProfileId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Candidate profile not found"
                                )
                        );

        Resume resume =
                resumeRepository.findById(resumeId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Resume not found"
                                )
                        );

        if (!resume.getCandidateProfile()
                .getId()
                .equals(candidateProfile.getId())) {

            throw new BusinessException(
                    "Resume does not belong to this candidate"
            );
        }

        if (!resume.isActive()) {

            throw new BusinessException(
                    "Inactive resume cannot be set as default"
            );
        }

        List<Resume> candidateResumes =
                resumeRepository.findByCandidateProfileId(
                        candidateProfileId
                );

        // Eski default CV'yi kaldır
        candidateResumes.stream()
                .filter(Resume::isDefaultResume)
                .forEach(existingResume -> {

                    existingResume.setDefaultResume(false);

                    resumeRepository.save(existingResume);
                });

        // Seçilen CV'yi default yap
        resume.setDefaultResume(true);

        return resumeRepository.save(resume);
    }
}
