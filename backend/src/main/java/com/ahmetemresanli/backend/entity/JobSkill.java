package com.ahmetemresanli.backend.entity;

import com.ahmetemresanli.backend.enums.SkillLevel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "job_skills",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"job_posting_id", "skill_id"}
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
public class JobSkill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "job_posting_id",
            nullable = false
    )
    private JobPosting jobPosting;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "skill_id",
            nullable = false
    )
    private Skill skill;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "required_skill_level",
            nullable = false,
            length = 20
    )
    private SkillLevel requiredSkillLevel;

    @Column(name = "required")
    private boolean required = true;

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