package com.ahmetemresanli.backend.mapper;

import com.ahmetemresanli.backend.dto.request.ApplicationCreateRequest;
import com.ahmetemresanli.backend.dto.response.ApplicationResponse;
import com.ahmetemresanli.backend.entity.Application;

public final class ApplicationMapper {

    private ApplicationMapper() {
    }

    public static Application toEntity(
            ApplicationCreateRequest request
    ) {

        if (request == null) {
            return null;
        }

        Application application = new Application();

        application.setCoverLetter(
                request.getCoverLetter()
        );

        return application;
    }

    public static ApplicationResponse toResponse(
            Application application
    ) {

        if (application == null) {
            return null;
        }

        ApplicationResponse response =
                new ApplicationResponse();

        response.setId(application.getId());

        // Candidate bilgileri
        if (application.getCandidateProfile() != null) {

            response.setCandidateProfileId(
                    application
                            .getCandidateProfile()
                            .getId()
            );

            response.setCandidateFirstName(
                    application
                            .getCandidateProfile()
                            .getFirstName()
            );

            response.setCandidateLastName(
                    application
                            .getCandidateProfile()
                            .getLastName()
            );
        }

        // JobPosting bilgileri
        if (application.getJobPosting() != null) {

            response.setJobPostingId(
                    application
                            .getJobPosting()
                            .getId()
            );

            response.setJobTitle(
                    application
                            .getJobPosting()
                            .getTitle()
            );

            // Company bilgileri
            if (application
                    .getJobPosting()
                    .getCompany() != null) {

                response.setCompanyId(
                        application
                                .getJobPosting()
                                .getCompany()
                                .getId()
                );

                response.setCompanyName(
                        application
                                .getJobPosting()
                                .getCompany()
                                .getName()
                );
            }
        }

        // Resume bilgileri
        if (application.getResume() != null) {

            response.setResumeId(
                    application
                            .getResume()
                            .getId()
            );

            response.setResumeFileName(
                    application
                            .getResume()
                            .getFileName()
            );
        }

        response.setStatus(
                application.getStatus()
        );

        response.setCoverLetter(
                application.getCoverLetter()
        );

        response.setCreatedAt(
                application.getCreatedAt()
        );

        response.setUpdatedAt(
                application.getUpdatedAt()
        );

        return response;
    }
}