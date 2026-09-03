package com.ahmetemresanli.backend.entity;

import com.ahmetemresanli.backend.enums.EducationLevel;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "educations")
public class Education {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "candidate_profile_id",
            nullable = false
    )
    private CandidateProfile candidateProfile;

    @Column(nullable = false, length = 200)
    private String universityName;

    @Column(nullable = false, length = 200)
    private String department;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private EducationLevel educationLevel;

    @Column(nullable = false)
    private LocalDate startDate;

    private LocalDate endDate;

    @Column(nullable = false)
    private boolean currentlyStudying = false;

    @Column(length = 1000)
    private String description;

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

    public void setEducationLevel(
            EducationLevel educationLevel
    ) {
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

    public void setCurrentlyStudying(
            boolean currentlyStudying
    ) {
        this.currentlyStudying = currentlyStudying;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}