package com.ahmetemresanli.backend.entity;

import com.ahmetemresanli.backend.enums.EducationVerificationType;
import com.ahmetemresanli.backend.enums.VerificationStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "education_verifications")
public class EducationVerification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "education_id",
            nullable = false
    )
    private Education education;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private EducationVerificationType verificationType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private VerificationStatus status =
            VerificationStatus.PENDING;

    /*
     * UNIVERSITY_EMAIL doğrulamasında kullanılır.
     * DOCUMENT doğrulamasında null olabilir.
     */
    @Column(length = 255)
    private String verificationEmail;

    /*
     * DOCUMENT doğrulamasında kullanılır.
     */
    @Column(length = 1000)
    private String documentUrl;

    /*
     * Email doğrulamasında kullanıcıya gönderilecek token.
     */
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

    public Education getEducation() {
        return education;
    }

    public void setEducation(Education education) {
        this.education = education;
    }

    public EducationVerificationType getVerificationType() {
        return verificationType;
    }

    public void setVerificationType(
            EducationVerificationType verificationType
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

    public void setToken(String token) {
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