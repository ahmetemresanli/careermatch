package com.ahmetemresanli.backend.entity;

import com.ahmetemresanli.backend.enums.SkillLevel;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "candidate_skills",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"candidate_profile_id", "skill_id"}
                )
        }
)
@Data
@NoArgsConstructor
public class CandidateSkill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "candidate_profile_id",
            nullable = false
    )
    private CandidateProfile candidateProfile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "skill_id",
            nullable = false
    )
    private Skill skill;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "skill_level",
            nullable = false,
            length = 20
    )
    private SkillLevel skillLevel;

    @Column(name = "years_of_experience")
    private Integer yearsOfExperience;

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
