package com.ahmetemresanli.backend.entity;

import jakarta.persistence.*;
import lombok.Getter; import lombok.Setter; import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp; import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDate; import java.time.LocalDateTime;

@Entity @Table(name = "certificates") @Getter @Setter @NoArgsConstructor
public class Certificate {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false) @JoinColumn(name="candidate_profile_id", nullable=false) private CandidateProfile candidateProfile;
    @Column(nullable=false, length=200) private String name;
    @Column(nullable=false, length=200) private String issuer;
    @Column(name="issue_date") private LocalDate issueDate;
    @Column(name="expiry_date") private LocalDate expiryDate;
    @Column(name="credential_id", length=255) private String credentialId;
    @Column(name="credential_url", length=1000) private String credentialUrl;
    @CreationTimestamp @Column(name="created_at", nullable=false, updatable=false) private LocalDateTime createdAt;
    @UpdateTimestamp @Column(name="updated_at", nullable=false) private LocalDateTime updatedAt;
}
