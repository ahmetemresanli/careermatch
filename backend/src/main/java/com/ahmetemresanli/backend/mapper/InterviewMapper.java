package com.ahmetemresanli.backend.mapper;

import com.ahmetemresanli.backend.dto.response.InterviewResponse;
import com.ahmetemresanli.backend.entity.Application;
import com.ahmetemresanli.backend.entity.CandidateProfile;
import com.ahmetemresanli.backend.entity.Interview;
import com.ahmetemresanli.backend.entity.JobPosting;

public final class InterviewMapper {

    private InterviewMapper() {
    }

    public static InterviewResponse toResponse(
            Interview interview
    ) {

        InterviewResponse response =
                new InterviewResponse();

        response.setId(
                interview.getId()
        );

        Application application =
                interview.getApplication();

        if (application != null) {

            response.setApplicationId(
                    application.getId()
            );

            response.setApplicationStatus(
                    application.getStatus()
            );

            CandidateProfile candidate =
                    application.getCandidateProfile();

            if (candidate != null) {

                response.setCandidateProfileId(
                        candidate.getId()
                );

                String firstName =
                        candidate.getFirstName() == null
                                ? ""
                                : candidate.getFirstName();

                String lastName =
                        candidate.getLastName() == null
                                ? ""
                                : candidate.getLastName();

                response.setCandidateName(
                        (firstName + " " + lastName).trim()
                );
            }

            JobPosting jobPosting =
                    application.getJobPosting();

            if (jobPosting != null) {

                response.setJobPostingId(
                        jobPosting.getId()
                );

                response.setJobTitle(
                        jobPosting.getTitle()
                );

                if (jobPosting.getCompany() != null) {

                    response.setCompanyId(
                            jobPosting
                                    .getCompany()
                                    .getId()
                    );

                    response.setCompanyName(
                            jobPosting
                                    .getCompany()
                                    .getName()
                    );
                }
            }
        }

        response.setInterviewType(
                interview.getInterviewType()
        );

        response.setInterviewMode(
                interview.getInterviewMode()
        );

        response.setStatus(
                interview.getStatus()
        );

        response.setScheduledAt(
                interview.getScheduledAt()
        );

        response.setDurationMinutes(
                interview.getDurationMinutes()
        );

        response.setMeetingUrl(
                interview.getMeetingUrl()
        );

        response.setLocation(
                interview.getLocation()
        );

        response.setNotes(
                interview.getNotes()
        );

        response.setFeedback(
                interview.getFeedback()
        );

        response.setCompletedAt(
                interview.getCompletedAt()
        );

        response.setCreatedAt(
                interview.getCreatedAt()
        );

        response.setUpdatedAt(
                interview.getUpdatedAt()
        );

        return response;
    }
}