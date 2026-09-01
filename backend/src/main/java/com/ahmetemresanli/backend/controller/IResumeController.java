package com.ahmetemresanli.backend.controller;

import com.ahmetemresanli.backend.entity.Resume;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IResumeController {

    ResponseEntity<Resume> createResume(
            Long candidateProfileId,
            Resume resume
    );

    ResponseEntity<Resume> getResumeById(
            Long id
    );

    ResponseEntity<List<Resume>> getResumesByCandidateProfileId(
            Long candidateProfileId
    );

    ResponseEntity<Resume> setDefaultResume(
            Long candidateProfileId,
            Long resumeId
    );
}