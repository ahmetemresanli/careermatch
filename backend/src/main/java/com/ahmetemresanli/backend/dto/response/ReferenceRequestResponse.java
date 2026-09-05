package com.ahmetemresanli.backend.dto.response;

import com.ahmetemresanli.backend.enums.ReferenceRelation;
import com.ahmetemresanli.backend.enums.ReferenceRequestStatus;

import java.time.LocalDateTime;

public class ReferenceRequestResponse {

    private Long id;
    private Long candidateProfileId;

    private String referenceName;
    private String referenceEmail;

    private ReferenceRelation relation;
    private String requestMessage;

    private ReferenceRequestStatus status;

    private String token;
    private LocalDateTime expiresAt;
    private LocalDateTime respondedAt;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ReferenceRequestResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getRequestMessage() {
        return requestMessage;
    }

    public void setRequestMessage(String requestMessage) {
        this.requestMessage = requestMessage;
    }

    public ReferenceRequestStatus getStatus() {
        return status;
    }

    public void setStatus(ReferenceRequestStatus status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    public LocalDateTime getRespondedAt() {
        return respondedAt;
    }

    public void setRespondedAt(LocalDateTime respondedAt) {
        this.respondedAt = respondedAt;
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