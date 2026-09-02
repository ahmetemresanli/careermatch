package com.ahmetemresanli.backend.dto.request;

import com.ahmetemresanli.backend.enums.JobStatus;
import jakarta.validation.constraints.NotNull;

public class JobPostingStatusUpdateRequest {

    @NotNull(message = "Job status cannot be null")
    private JobStatus status;

    public JobPostingStatusUpdateRequest() {
    }

    public JobStatus getStatus() {
        return status;
    }

    public void setStatus(JobStatus status) {
        this.status = status;
    }
}