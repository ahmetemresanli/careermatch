package com.ahmetemresanli.backend.entity;

import com.ahmetemresanli.backend.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 255)
    private String email;

    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash;

    @Column(name = "recovery_email", length = 255)
    private String recoveryEmail;

    @Column(name = "email_verified", nullable = false)
    private boolean emailVerified = false;

    @Column(name = "recovery_email_verified", nullable = false)
    private boolean recoveryEmailVerified = false;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private UserRole role;

    @Column(nullable = false)
    private boolean active = true;

    @OneToOne(
            mappedBy = "user",
            fetch = FetchType.LAZY
    )
    @JsonIgnore
    private CandidateProfile candidateProfile;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<CompanyMember> companyMemberships = new ArrayList<>();

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
