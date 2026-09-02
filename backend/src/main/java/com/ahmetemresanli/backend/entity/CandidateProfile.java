package com.ahmetemresanli.backend.entity;

import com.ahmetemresanli.backend.enums.JobSearchStatus;
import com.ahmetemresanli.backend.enums.WorkModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "candidate_profiles")
@NoArgsConstructor
public class CandidateProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(
            name = "user_id",
            nullable = false,
            unique = true
    )
    private User user;

    @Column(name = "first_name",nullable = false,length = 100)
    private String firstName;

    @Column(name = "last_name",nullable = false,length = 100)
    private String lastName;

    @Column(columnDefinition = "TEXT")
    private String about;

    @Column(length = 100)
    private String city;

    @Column(length = 100)
    private String country;

    @Column(name = "github_url", length = 500)
    private String githubUrl;

    @Column(name = "linkedin_url", length = 500)
    private String linkedinUrl;

    @Column(name = "website_url", length = 500)
    private String websiteUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "job_search_status", nullable = false, length = 30)
    private JobSearchStatus jobSearchStatus = JobSearchStatus.OPEN_TO_WORK;

    @Column(name = "visible_to_recruiters", nullable = false)
    private boolean visibleToRecruiters = true;

    @Column(name = "expected_min_salary", precision = 12, scale = 2)
    private BigDecimal expectedMinSalary;

    @Column(name = "expected_max_salary", precision = 12, scale = 2)
    private BigDecimal expectedMaxSalary;

    @Enumerated(EnumType.STRING)
    @Column(name = "preferred_work_model", length = 20)
    private WorkModel preferredWorkModel;

    @OneToMany(mappedBy = "candidateProfile")
    @JsonIgnore
    private List<CandidateSkill> candidateSkills = new ArrayList<>();

    @OneToMany(mappedBy = "candidateProfile")
    @JsonIgnore
    private List<Resume> resumes = new ArrayList<>();

    @OneToMany(mappedBy = "candidateProfile")
    @JsonIgnore
    private List<Application> applications = new ArrayList<>();

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
