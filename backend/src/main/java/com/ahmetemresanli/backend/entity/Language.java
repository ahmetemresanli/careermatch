package com.ahmetemresanli.backend.entity;

import jakarta.persistence.*;
import lombok.Getter; import lombok.Setter; import lombok.NoArgsConstructor;

@Entity @Table(name="languages") @Getter @Setter @NoArgsConstructor
public class Language {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false, unique=true, length=100) private String name;
    @Column(nullable=false) private boolean active=true;
}
