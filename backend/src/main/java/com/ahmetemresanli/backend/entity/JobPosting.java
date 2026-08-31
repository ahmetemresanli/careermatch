package com.ahmetemresanli.backend.entity;

import com.ahmetemresanli.backend.enums.EmploymentType;
import com.ahmetemresanli.backend.enums.JobLevel;
import com.ahmetemresanli.backend.enums.JobStatus;
import com.ahmetemresanli.backend.enums.WorkModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "job_postings")
@Getter
@Setter
@NoArgsConstructor
public class JobPosting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "company_id",
            nullable = false
    )
    private Company company;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String description;

    @Column(length = 100)
    private String city;

    @Column(length = 100)
    private String country;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "employment_type",
            nullable = false,
            length = 30
    )
    private EmploymentType employmentType;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "work_model",
            nullable = false,
            length = 20
    )
    private WorkModel workModel;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "job_level",
            nullable = false,
            length = 20
    )
    private JobLevel jobLevel;

    @Column(name = "minimum_experience_years")
    private Integer minimumExperienceYears;

    @Column(
            name = "minimum_salary",
            precision = 12,
            scale = 2
    )
    private BigDecimal minimumSalary;

    @Column(
            name = "maximum_salary",
            precision = 12,
            scale = 2
    )
    private BigDecimal maximumSalary;

    @Enumerated(EnumType.STRING)
    @Column(
            nullable = false,
            length = 20
    )
    private JobStatus status = JobStatus.DRAFT;

    @Column(name = "application_deadline")
    private LocalDateTime applicationDeadline;

    @OneToMany(mappedBy = "jobPosting")
    @JsonIgnore
    private List<JobSkill> jobSkills = new ArrayList<>();

    @CreationTimestamp
    @Column(
            name = "created_at",
            nullable = false,
            updatable = false
    )
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(
            name = "updated_at",
            nullable = false
    )
    private LocalDateTime updatedAt;
}