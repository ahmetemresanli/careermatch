package com.ahmetemresanli.backend.service;

import com.ahmetemresanli.backend.dto.response.AuditLogResponse;
import org.springframework.data.domain.Page;

public interface IAuditLogService {
    void record(Long actorUserId, String action, String entityType, Object entityId, String details);
    Page<AuditLogResponse> list(int page, int size);
}
