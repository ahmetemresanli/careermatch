package com.ahmetemresanli.backend.dto.request;

import com.ahmetemresanli.backend.enums.ReferenceRelation;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ReferenceRequestCreateRequest {

    @NotBlank(message = "Reference name cannot be empty")
    @Size(max = 200, message = "Reference name cannot exceed 200 characters")
    private String referenceName;

    @NotBlank(message = "Reference email cannot be empty")
    @Email(message = "Invalid email format")
    @Size(max = 255, message = "Reference email cannot exceed 255 characters")
    private String referenceEmail;

    @NotNull(message = "Reference relation cannot be null")
    private ReferenceRelation relation;

    @Size(max = 1000, message = "Request message cannot exceed 1000 characters")
    private String requestMessage;

    public ReferenceRequestCreateRequest() {
    }

    public String getReferenceName() {
        return referenceName;
    }

    public void setReferenceName(String referenceName) {
        this.referenceName = referenceName;
    }

    public String getReferenceEmail() {
        return referenceEmail;
    }

    public void setReferenceEmail(String referenceEmail) {
        this.referenceEmail = referenceEmail;
    }

    public ReferenceRelation getRelation() {
        return relation;
    }

    public void setRelation(ReferenceRelation relation) {
        this.relation = relation;
    }

    public String getRequestMessage() {
        return requestMessage;
    }

    public void setRequestMessage(String requestMessage) {
        this.requestMessage = requestMessage;
    }
}