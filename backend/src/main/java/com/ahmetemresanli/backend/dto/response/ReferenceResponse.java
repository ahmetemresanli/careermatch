package com.ahmetemresanli.backend.dto.response;

import com.ahmetemresanli.backend.enums.ReferenceRelation;

import java.time.LocalDateTime;

public class ReferenceResponse {

    private Long id;
    private Long referenceRequestId;
    private Long candidateProfileId;

    private String referenceName;
    private String referenceEmail;

    private ReferenceRelation relation;

    private String referenceText;
    private String organizationName;
    private String positionTitle;

    private boolean visible;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ReferenceResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getReferenceRequestId() {
        return referenceRequestId;
    }

    public void setReferenceRequestId(Long referenceRequestId) {
        this.referenceRequestId = referenceRequestId;
    }

    public Long getCandidateProfileId() {
        return candidateProfileId;
    }

    public void setCandidateProfileId(Long candidateProfileId) {
        this.candidateProfileId = candidateProfileId;
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

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}