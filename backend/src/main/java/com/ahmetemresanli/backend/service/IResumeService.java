package com.ahmetemresanli.backend.service;

import com.ahmetemresanli.backend.entity.Resume;

import java.util.List;

public interface IResumeService {

    Resume createResume(
            Long candidateProfileId,
            Resume resume
    );

    Resume getResumeById(Long id);

    List<Resume> getResumesByCandidateProfileId(
            Long candidateProfileId
    );

    Resume setDefaultResume(
            Long candidateProfileId,
            Long resumeId
    );
}