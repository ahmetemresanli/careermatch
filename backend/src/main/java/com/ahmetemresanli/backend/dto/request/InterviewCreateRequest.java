package com.ahmetemresanli.backend.dto.request;

import com.ahmetemresanli.backend.enums.InterviewMode;
import com.ahmetemresanli.backend.enums.InterviewType;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public class InterviewCreateRequest {

    @NotNull(message = "Interview type cannot be null")
    private InterviewType interviewType;

    @NotNull(message = "Interview mode cannot be null")
    private InterviewMode interviewMode;

    @NotNull(message = "Scheduled date cannot be null")
    @Future(message = "Interview must be scheduled for a future date")
    private LocalDateTime scheduledAt;

    @NotNull(message = "Duration cannot be null")
    @Positive(message = "Duration must be greater than zero")
    private Integer durationMinutes;

    @Size(max = 1000)
    private String meetingUrl;

    @Size(max = 1000)
    private String location;

    @Size(max = 2000)
    private String notes;

    public InterviewCreateRequest() {
    }

    public InterviewType getInterviewType() {
        return interviewType;
    }

    public void setInterviewType(InterviewType interviewType) {
        this.interviewType = interviewType;
    }

    public InterviewMode getInterviewMode() {
        return interviewMode;
    }

    public void setInterviewMode(InterviewMode interviewMode) {
        this.interviewMode = interviewMode;
    }

    public LocalDateTime getScheduledAt() {
        return scheduledAt;
    }

    public void setScheduledAt(LocalDateTime scheduledAt) {
        this.scheduledAt = scheduledAt;
    }

    public Integer getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(Integer durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public String getMeetingUrl() {
        return meetingUrl;
    }

    public void setMeetingUrl(String meetingUrl) {
        this.meetingUrl = meetingUrl;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}