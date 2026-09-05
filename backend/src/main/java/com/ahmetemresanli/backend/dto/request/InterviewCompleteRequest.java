package com.ahmetemresanli.backend.dto.request;

import jakarta.validation.constraints.Size;

public class InterviewCompleteRequest {

    @Size(
            max = 3000,
            message = "Feedback cannot exceed 3000 characters"
    )
    private String feedback;

    public InterviewCompleteRequest() {
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}