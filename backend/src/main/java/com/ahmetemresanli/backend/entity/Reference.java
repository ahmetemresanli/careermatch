package com.ahmetemresanli.backend.entity;

import com.ahmetemresanli.backend.enums.ReferenceRelation;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "candidate_references")
public class Reference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "reference_request_id",
            nullable = false,
            unique = true
    )
    private ReferenceRequest referenceRequest;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "candidate_profile_id",
            nullable = false
    )
    private CandidateProfile candidateProfile;

    @Column(nullable = false, length = 200)
    private String referenceName;

    @Column(nullable = false, length = 255)
    private String referenceEmail;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private ReferenceRelation relation;

    @Column(nullable = false, length = 3000)
    private String referenceText;

    /*
     * Referansı veren kişinin şirket / kurum bilgisi.
     * Zorunlu değil.
     */
    @Column(length = 200)
    private String organizationName;

    /*
     * Referansı veren kişinin pozisyonu.
     * Örn: Engineering Manager
     */
    @Column(length = 200)
    private String positionTitle;

    /*
     * Aday bu referansın profilinde
     * görünmesini istemeyebilir.
     */
    @Column(nullable = false)
    private boolean visible = true;

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

    public ReferenceRequest getReferenceRequest() {
        return referenceRequest;
    }

    public void setReferenceRequest(
            ReferenceRequest referenceRequest
    ) {
        this.referenceRequest = referenceRequest;
    }

    public CandidateProfile getCandidateProfile() {
        return candidateProfile;
    }

    public void setCandidateProfile(
            CandidateProfile candidateProfile
    ) {
        this.candidateProfile = candidateProfile;
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

    public void setRelation(
            ReferenceRelation relation
    ) {
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

    public void setOrganizationName(
            String organizationName
    ) {
        this.organizationName = organizationName;
    }

    public String getPositionTitle() {
        return positionTitle;
    }

    public void setPositionTitle(
            String positionTitle
    ) {
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

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}