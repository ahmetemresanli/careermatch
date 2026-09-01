package com.ahmetemresanli.backend.service.impl;

import com.ahmetemresanli.backend.entity.CandidateProfile;
import com.ahmetemresanli.backend.entity.Resume;
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
                                new IllegalArgumentException(
                                        "Candidate profile not found"
                                )
                        );

        if (resume.getFileName() == null ||
                resume.getFileName().isBlank()) {

            throw new IllegalArgumentException(
                    "File name cannot be empty"
            );
        }

        if (resume.getFileUrl() == null ||
                resume.getFileUrl().isBlank()) {

            throw new IllegalArgumentException(
                    "File URL cannot be empty"
            );
        }

        Optional<Resume> currentDefaultResume =
                resumeRepository
                        .findByCandidateProfileIdAndDefaultResumeTrue(
                                candidateProfileId
                        );

        /*
         * Eğer adayın hiç varsayılan CV'si yoksa,
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
                        new IllegalArgumentException(
                                "Resume not found"
                        )
                );
    }

    @Override
    public List<Resume> getResumesByCandidateProfileId(
            Long candidateProfileId
    ) {

        if (!candidateProfileRepository.existsById(candidateProfileId)) {

            throw new IllegalArgumentException(
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
                                new IllegalArgumentException(
                                        "Resume not found"
                                )
                        );

        if (!resume.getCandidateProfile()
                .getId()
                .equals(candidateProfileId)) {

            throw new IllegalArgumentException(
                    "Resume does not belong to this candidate"
            );
        }

        if (!resume.isActive()) {

            throw new IllegalArgumentException(
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