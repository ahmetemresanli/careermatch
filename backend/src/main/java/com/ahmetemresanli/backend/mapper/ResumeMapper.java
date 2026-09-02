package com.ahmetemresanli.backend.mapper;

import com.ahmetemresanli.backend.dto.request.ResumeCreateRequest;
import com.ahmetemresanli.backend.dto.response.ResumeResponse;
import com.ahmetemresanli.backend.entity.Resume;

public final class ResumeMapper {

    private ResumeMapper() {
    }

    public static Resume toEntity(
            ResumeCreateRequest request
    ) {

        if (request == null) {
            return null;
        }

        Resume resume = new Resume();

        resume.setFileName(request.getFileName());
        resume.setFileUrl(request.getFileUrl());
        resume.setContentType(request.getContentType());

        if (request.getDefaultResume() != null) {
            resume.setDefaultResume(
                    request.getDefaultResume()
            );
        }

        return resume;
    }

    public static ResumeResponse toResponse(
            Resume resume
    ) {

        if (resume == null) {
            return null;
        }

        ResumeResponse response = new ResumeResponse();

        response.setId(resume.getId());

        if (resume.getCandidateProfile() != null) {
            response.setCandidateProfileId(
                    resume.getCandidateProfile().getId()
            );
        }

        response.setFileName(resume.getFileName());
        response.setFileUrl(resume.getFileUrl());
        response.setContentType(resume.getContentType());
        response.setDefaultResume(resume.isDefaultResume());
        response.setActive(resume.isActive());
        response.setCreatedAt(resume.getCreatedAt());
        response.setUpdatedAt(resume.getUpdatedAt());

        return response;
    }
}