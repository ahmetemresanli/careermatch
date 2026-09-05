package com.ahmetemresanli.backend.dto.response;

import java.time.LocalDateTime;

public record AuditLogResponse(Long id, Long actorUserId, String action, String entityType, String entityId,
                               String details, LocalDateTime createdAt) { }
