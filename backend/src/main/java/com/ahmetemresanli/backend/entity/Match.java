package com.ahmetemresanli.backend.entity;

import com.ahmetemresanli.backend.enums.MatchStatus;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "matches",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_match_candidate_job",
                        columnNames = {
                                "candidate_profile_id",
                                "job_posting_id"
                        }
                )
        }
)
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "candidate_profile_id",
            nullable = false
    )
    private CandidateProfile candidateProfile;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "job_posting_id",
            nullable = false
    )
    private JobPosting jobPosting;

    @Column(
            nullable = false,
            precision = 5,
            scale = 2
    )
    private BigDecimal score;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private MatchStatus status = MatchStatus.ACTIVE;

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

    public void setId(Long id) {
        this.id = id;
    }

    public CandidateProfile getCandidateProfile() {
        return candidateProfile;
    }

    public void setCandidateProfile(
            CandidateProfile candidateProfile
    ) {
        this.candidateProfile = candidateProfile;
    }

    public JobPosting getJobPosting() {
        return jobPosting;
    }

    public void setJobPosting(
            JobPosting jobPosting
    ) {
        this.jobPosting = jobPosting;
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public MatchStatus getStatus() {
        return status;
    }

    public void setStatus(MatchStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}