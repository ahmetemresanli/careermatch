package com.ahmetemresanli.backend.entity;

import jakarta.persistence.*;
import lombok.Getter; import lombok.Setter; import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp; import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDate; import java.time.LocalDateTime;

@Entity @Table(name = "candidate_projects") @Getter @Setter @NoArgsConstructor
public class Project {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false) @JoinColumn(name = "candidate_profile_id", nullable = false) private CandidateProfile candidateProfile;
    @Column(nullable = false, length = 200) private String name;
    @Column(length = 3000) private String description;
    @Column(name = "project_url", length = 1000) private String projectUrl;
    @Column(name = "repository_url", length = 1000) private String repositoryUrl;
    @Column(name = "start_date") private LocalDate startDate;
    @Column(name = "end_date") private LocalDate endDate;
    @CreationTimestamp @Column(name="created_at", nullable=false, updatable=false) private LocalDateTime createdAt;
    @UpdateTimestamp @Column(name="updated_at", nullable=false) private LocalDateTime updatedAt;
}
