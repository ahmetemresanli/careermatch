package com.ahmetemresanli.backend.dto.response;

import com.ahmetemresanli.backend.enums.NotificationType;
import java.time.LocalDateTime;

public record NotificationResponse(Long id, NotificationType type, String title, String message, String payload,
                                   boolean read, LocalDateTime readAt, LocalDateTime createdAt) { }
