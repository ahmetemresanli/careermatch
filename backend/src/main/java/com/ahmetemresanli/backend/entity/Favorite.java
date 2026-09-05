package com.ahmetemresanli.backend.entity;

import jakarta.persistence.*;
import lombok.Getter; import lombok.Setter; import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity @Table(name="favorites", uniqueConstraints=@UniqueConstraint(columnNames={"candidate_profile_id","job_posting_id"}))
@Getter @Setter @NoArgsConstructor
public class Favorite {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch=FetchType.LAZY, optional=false) @JoinColumn(name="candidate_profile_id", nullable=false) private CandidateProfile candidateProfile;
    @ManyToOne(fetch=FetchType.LAZY, optional=false) @JoinColumn(name="job_posting_id", nullable=false) private JobPosting jobPosting;
    @CreationTimestamp @Column(name="created_at", nullable=false, updatable=false) private LocalDateTime createdAt;
}
