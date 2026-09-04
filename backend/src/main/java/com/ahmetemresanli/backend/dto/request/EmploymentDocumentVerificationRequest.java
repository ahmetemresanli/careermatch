package com.ahmetemresanli.backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class EmploymentDocumentVerificationRequest {

    @NotBlank(message = "Document URL cannot be empty")
    @Size(max = 1000, message = "Document URL cannot exceed 1000 characters")
    private String documentUrl;

    public EmploymentDocumentVerificationRequest() {
    }

    public String getDocumentUrl() {
        return documentUrl;
    }

    public void setDocumentUrl(String documentUrl) {
        this.documentUrl = documentUrl;
    }
}