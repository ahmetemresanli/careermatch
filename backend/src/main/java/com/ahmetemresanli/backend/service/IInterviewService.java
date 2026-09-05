package com.ahmetemresanli.backend.service;

import com.ahmetemresanli.backend.entity.Interview;
import com.ahmetemresanli.backend.enums.InterviewMode;
import com.ahmetemresanli.backend.enums.InterviewType;

import java.time.LocalDateTime;
import java.util.List;

public interface IInterviewService {

    Interview createInterview(
            Long applicationId,
            InterviewType interviewType,
            InterviewMode interviewMode,
            LocalDateTime scheduledAt,
            Integer durationMinutes,
            String meetingUrl,
            String location,
            String notes
    );

    Interview completeInterview(
            Long interviewId,
            String feedback
    );

    Interview cancelInterview(
            Long interviewId
    );

    Interview getInterviewById(
            Long interviewId
    );

    List<Interview> getInterviewsByApplication(
            Long applicationId
    );

    List<Interview> getInterviewsByCandidate(
            Long candidateProfileId
    );

    List<Interview> getInterviewsByCompany(
            Long companyId
    );
}