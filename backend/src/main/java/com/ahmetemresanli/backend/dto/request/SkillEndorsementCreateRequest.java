package com.ahmetemresanli.backend.dto.request;

import com.ahmetemresanli.backend.enums.ReferenceRelation;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class SkillEndorsementCreateRequest {

    @NotBlank(message = "Endorser name cannot be empty")
    @Size(max = 200)
    private String endorserName;

    @NotBlank(message = "Endorser email cannot be empty")
    @Email(message = "Invalid email format")
    @Size(max = 255)
    private String endorserEmail;

    @NotNull(message = "Relation cannot be null")
    private ReferenceRelation relation;

    @Size(max = 1000)
    private String requestMessage;

    public SkillEndorsementCreateRequest() {
    }

    public String getEndorserName() {
        return endorserName;
    }

    public void setEndorserName(String endorserName) {
        this.endorserName = endorserName;
    }

    public String getEndorserEmail() {
        return endorserEmail;
    }

    public void setEndorserEmail(String endorserEmail) {
        this.endorserEmail = endorserEmail;
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