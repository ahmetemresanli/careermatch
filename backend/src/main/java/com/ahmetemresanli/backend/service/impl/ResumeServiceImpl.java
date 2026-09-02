package com.ahmetemresanli.backend.service.impl;

import com.ahmetemresanli.backend.entity.CandidateProfile;
import com.ahmetemresanli.backend.entity.Resume;
import com.ahmetemresanli.backend.exception.BusinessException;
import com.ahmetemresanli.backend.exception.ResourceNotFoundException;
import com.ahmetemresanli.backend.repository.CandidateProfileRepository;
import com.ahmetemresanli.backend.repository.ResumeRepository;
import com.ahmetemresanli.backend.service.IResumeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
                    "File name cannot be empty"
            );
        }

        if (resume.getFileUrl() == null
                || resume.getFileUrl().isBlank()) {

            throw new BusinessException(
                    "File URL cannot be empty"
            );
        }

        Optional<Resume> currentDefaultResume =
                resumeRepository
                        .findByCandidateProfileIdAndDefaultResumeTrue(
                                candidateProfileId
                        );

        /*
         * Adayın hiç varsayılan CV'si yoksa,
         * oluşturulan ilk CV otomatik olarak default olur.
         */
        if (currentDefaultResume.isEmpty()) {

            resume.setDefaultResume(true);

        } else if (resume.isDefaultResume()) {

            Resume oldDefaultResume =
                    currentDefaultResume.get();

            oldDefaultResume.setDefaultResume(false);

            resumeRepository.save(oldDefaultResume);
        }

        resume.setCandidateProfile(candidateProfile);
        resume.setActive(true);

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

        return resumeRepository
                .findByCandidateProfileId(candidateProfileId);
    }

    @Override
    public Resume setDefaultResume(
            Long candidateProfileId,
            Long resumeId
    ) {

        Resume resume =
                resumeRepository.findById(resumeId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Resume not found"
                                )
                        );

        if (!resume.getCandidateProfile()
                .getId()
                .equals(candidateProfileId)) {

            throw new BusinessException(
                    "Resume does not belong to this candidate"
            );
        }

        if (!resume.isActive()) {

            throw new BusinessException(
                    "Inactive resume cannot be default"
            );
        }

        Optional<Resume> currentDefaultResume =
                resumeRepository
                        .findByCandidateProfileIdAndDefaultResumeTrue(
                                candidateProfileId
                        );

        if (currentDefaultResume.isPresent()) {

            Resume oldDefaultResume =
                    currentDefaultResume.get();

            oldDefaultResume.setDefaultResume(false);

            resumeRepository.save(oldDefaultResume);
        }

        resume.setDefaultResume(true);

        return resumeRepository.save(resume);
    }
}