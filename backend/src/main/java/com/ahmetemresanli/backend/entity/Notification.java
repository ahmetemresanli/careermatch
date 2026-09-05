package com.ahmetemresanli.backend.entity;

import com.ahmetemresanli.backend.enums.NotificationType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity @Table(name = "notifications", indexes = @Index(name = "idx_notification_user_read", columnList = "user_id,read"))
@Getter @Setter @NoArgsConstructor
public class Notification {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false) @JoinColumn(name = "user_id", nullable = false) private User user;
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 40) private NotificationType type;
    @Column(nullable = false, length = 200) private String title;
    @Column(nullable = false, length = 2000) private String message;
    @Column(columnDefinition = "TEXT") private String payload;
    @Column(nullable = false) private boolean read = false;
    @Column(name = "read_at") private LocalDateTime readAt;
    @CreationTimestamp @Column(name = "created_at", nullable = false, updatable = false) private LocalDateTime createdAt;
}
