package com.ahmetemresanli.backend.entity;

import com.ahmetemresanli.backend.enums.EmploymentVerificationType;
import com.ahmetemresanli.backend.enums.VerificationStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "employment_verifications")
public class EmploymentVerification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "experience_id",
            nullable = false
    )
    private Experience experience;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private EmploymentVerificationType verificationType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private VerificationStatus status =
            VerificationStatus.PENDING;

    @Column(length = 255)
    private String verificationEmail;

    @Column(length = 1000)
    private String documentUrl;

    @Column(unique = true, length = 255)
    private String token;

    private LocalDateTime expiresAt;

    private LocalDateTime verifiedAt;

    @Column(length = 1000)
    private String rejectionReason;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {

        LocalDateTime now = LocalDateTime.now();

        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public Experience getExperience() {
        return experience;
    }

    public void setExperience(
            Experience experience
    ) {
        this.experience = experience;
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

    public void setStatus(
            VerificationStatus status
    ) {
        this.status = status;
    }

    public String getVerificationEmail() {
        return verificationEmail;
    }

    public void setVerificationEmail(
            String verificationEmail
    ) {
        this.verificationEmail = verificationEmail;
    }

    public String getDocumentUrl() {
        return documentUrl;
    }

    public void setDocumentUrl(
            String documentUrl
    ) {
        this.documentUrl = documentUrl;
    }

    public String getToken() {
        return token;
    }

    public void setToken(
            String token
    ) {
        this.token = token;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(
            LocalDateTime expiresAt
    ) {
        this.expiresAt = expiresAt;
    }

    public LocalDateTime getVerifiedAt() {
        return verifiedAt;
    }

    public void setVerifiedAt(
            LocalDateTime verifiedAt
    ) {
        this.verifiedAt = verifiedAt;
    }

    public String getRejectionReason() {
        return rejectionReason;
    }

    public void setRejectionReason(
            String rejectionReason
    ) {
        this.rejectionReason = rejectionReason;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}