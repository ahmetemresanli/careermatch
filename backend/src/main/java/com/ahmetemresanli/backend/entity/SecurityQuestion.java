package com.ahmetemresanli.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "security_questions")
@Getter @Setter @NoArgsConstructor
public class SecurityQuestion {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true, length = 300)
    private String question;
    @Column(nullable = false)
    private boolean active = true;
}
