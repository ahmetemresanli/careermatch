package com.ahmetemresanli.backend.dto.response;

import com.ahmetemresanli.backend.enums.EducationLevel;
import com.ahmetemresanli.backend.enums.EducationVerificationType;
import com.ahmetemresanli.backend.enums.VerificationStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class EducationResponse {

    private Long id;
    private Long candidateProfileId;

    private String universityName;
    private String department;
    private EducationLevel educationLevel;

    private LocalDate startDate;
    private LocalDate endDate;

    private boolean currentlyStudying;

    private String description;

    // Verification bilgileri
    private boolean verified;
    private VerificationStatus verificationStatus;
    private EducationVerificationType verificationType;
    private LocalDateTime verifiedAt;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public EducationResponse() {
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

    public String getUniversityName() {
        return universityName;
    }

    public void setUniversityName(String universityName) {
        this.universityName = universityName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public EducationLevel getEducationLevel() {
        return educationLevel;
    }

    public void setEducationLevel(EducationLevel educationLevel) {
        this.educationLevel = educationLevel;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public boolean isCurrentlyStudying() {
        return currentlyStudying;
    }

    public void setCurrentlyStudying(boolean currentlyStudying) {
        this.currentlyStudying = currentlyStudying;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public VerificationStatus getVerificationStatus() {
        return verificationStatus;
    }

    public void setVerificationStatus(
            VerificationStatus verificationStatus
    ) {
        this.verificationStatus = verificationStatus;
    }

    public EducationVerificationType getVerificationType() {
        return verificationType;
    }

    public void setVerificationType(
            EducationVerificationType verificationType
    ) {
        this.verificationType = verificationType;
    }

    public LocalDateTime getVerifiedAt() {
        return verifiedAt;
    }

    public void setVerifiedAt(LocalDateTime verifiedAt) {
        this.verifiedAt = verifiedAt;
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