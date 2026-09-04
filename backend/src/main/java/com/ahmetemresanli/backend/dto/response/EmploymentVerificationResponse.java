package com.ahmetemresanli.backend.dto.response;

import com.ahmetemresanli.backend.enums.EmploymentVerificationType;
import com.ahmetemresanli.backend.enums.VerificationStatus;

import java.time.LocalDateTime;

public class EmploymentVerificationResponse {

    private Long id;

    private Long experienceId;
    private Long candidateProfileId;

    private EmploymentVerificationType verificationType;
    private VerificationStatus status;

    private String verificationEmail;
    private String documentUrl;

    private String token;

    private LocalDateTime expiresAt;
    private LocalDateTime verifiedAt;

    private String rejectionReason;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public EmploymentVerificationResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getExperienceId() {
        return experienceId;
    }

    public void setExperienceId(Long experienceId) {
        this.experienceId = experienceId;
    }

    public Long getCandidateProfileId() {
        return candidateProfileId;
    }

    public void setCandidateProfileId(Long candidateProfileId) {
        this.candidateProfileId = candidateProfileId;
    }

    public EmploymentVerificationType getVerificationType() {
        return verificationType;
    }

    public void setVerificationType(
            EmploymentVerificationType verificationType
    ) {
        this.verificationType = verificationType;
    }

    public VerificationStatus getStatus() {
        return status;
    }

    public void setStatus(VerificationStatus status) {
        this.status = status;
    }

    public String getVerificationEmail() {
        return verificationEmail;
    }

    public void setVerificationEmail(String verificationEmail) {
        this.verificationEmail = verificationEmail;
    }

    public String getDocumentUrl() {
        return documentUrl;
    }

    public void setDocumentUrl(String documentUrl) {
        this.documentUrl = documentUrl;
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

    public LocalDateTime getVerifiedAt() {
        return verifiedAt;
    }

    public void setVerifiedAt(LocalDateTime verifiedAt) {
        this.verifiedAt = verifiedAt;
    }

    public String getRejectionReason() {
        return rejectionReason;
    }

    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
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