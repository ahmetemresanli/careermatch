package com.ahmetemresanli.backend.dto.request;

import jakarta.validation.constraints.Size;

public class SkillEndorsementAcceptRequest {

    @Size(max = 2000)
    private String endorsementComment;

    public SkillEndorsementAcceptRequest() {
    }

    public String getEndorsementComment() {
        return endorsementComment;
    }

    public void setEndorsementComment(String endorsementComment) {
        this.endorsementComment = endorsementComment;
    }
}