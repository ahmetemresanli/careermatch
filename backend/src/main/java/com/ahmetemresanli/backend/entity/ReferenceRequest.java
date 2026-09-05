package com.ahmetemresanli.backend.entity;

import com.ahmetemresanli.backend.enums.ReferenceRelation;
import com.ahmetemresanli.backend.enums.ReferenceRequestStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "reference_requests")
public class ReferenceRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "candidate_profile_id",
            nullable = false
    )
    private CandidateProfile candidateProfile;

    @Column(nullable = false, length = 255)
    private String referenceEmail;

    @Column(nullable = false, length = 200)
    private String referenceName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private ReferenceRelation relation;

    @Column(length = 1000)
    private String requestMessage;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private ReferenceRequestStatus status =
            ReferenceRequestStatus.PENDING;

    @Column(unique = true, nullable = false, length = 255)
    private String token;

    @Column(nullable = false)
    private LocalDateTime expiresAt;

    private LocalDateTime respondedAt;

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

    public CandidateProfile getCandidateProfile() {
        return candidateProfile;
    }

    public void setCandidateProfile(
            CandidateProfile candidateProfile
    ) {
        this.candidateProfile = candidateProfile;
    }

    public String getReferenceEmail() {
        return referenceEmail;
    }

    public void setReferenceEmail(String referenceEmail) {
        this.referenceEmail = referenceEmail;
    }

    public String getReferenceName() {
        return referenceName;
    }

    public void setReferenceName(String referenceName) {
        this.referenceName = referenceName;
    }

    public ReferenceRelation getRelation() {
        return relation;
    }

    public void setRelation(
            ReferenceRelation relation
    ) {
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

    public void setStatus(
            ReferenceRequestStatus status
    ) {
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

    public void setExpiresAt(
            LocalDateTime expiresAt
    ) {
        this.expiresAt = expiresAt;
    }

    public LocalDateTime getRespondedAt() {
        return respondedAt;
    }

    public void setRespondedAt(
            LocalDateTime respondedAt
    ) {
        this.respondedAt = respondedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}