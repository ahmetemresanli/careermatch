package com.ahmetemresanli.backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ReferenceAcceptRequest {

    @NotBlank(message = "Reference text cannot be empty")
    @Size(max = 3000, message = "Reference text cannot exceed 3000 characters")
    private String referenceText;

    @Size(max = 200, message = "Organization name cannot exceed 200 characters")
    private String organizationName;

    @Size(max = 200, message = "Position title cannot exceed 200 characters")
    private String positionTitle;

    public ReferenceAcceptRequest() {
    }

    public String getReferenceText() {
        return referenceText;
    }

    public void setReferenceText(String referenceText) {
        this.referenceText = referenceText;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getPositionTitle() {
        return positionTitle;
    }

    public void setPositionTitle(String positionTitle) {
        this.positionTitle = positionTitle;
    }
}