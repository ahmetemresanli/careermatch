package com.ahmetemresanli.backend.service.impl;

import com.ahmetemresanli.backend.dto.response.AuditLogResponse;
import com.ahmetemresanli.backend.entity.AuditLog;
import com.ahmetemresanli.backend.repository.AuditLogRepository;
import com.ahmetemresanli.backend.repository.UserRepository;
import com.ahmetemresanli.backend.service.IAuditLogService;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
public class AuditLogServiceImpl implements IAuditLogService {
    private final AuditLogRepository logs; private final UserRepository users;
    public AuditLogServiceImpl(AuditLogRepository logs, UserRepository users) { this.logs = logs; this.users = users; }
    @Override public void record(Long actorUserId, String action, String entityType, Object entityId, String details) {
        AuditLog log = new AuditLog();
        if (actorUserId != null) users.findById(actorUserId).ifPresent(log::setActorUser);
        log.setAction(limit(action, 100)); log.setEntityType(limit(entityType, 100));
        log.setEntityId(entityId == null ? null : limit(entityId.toString(), 100));
        log.setDetails(limit(details, 2000)); logs.save(log);
    }
    @Override public Page<AuditLogResponse> list(int page, int size) {
        if (page < 0 || size < 1 || size > 100) throw new IllegalArgumentException("Invalid pagination");
        return logs.findAllByOrderByCreatedAtDesc(PageRequest.of(page, size)).map(this::map);
    }
    private AuditLogResponse map(AuditLog log) {
        return new AuditLogResponse(log.getId(), log.getActorUser() == null ? null : log.getActorUser().getId(),
                log.getAction(), log.getEntityType(), log.getEntityId(), log.getDetails(), log.getCreatedAt());
    }
    private String limit(String value, int max) {
        if (value == null) return null;
        String safe = value.replaceAll("(?i)(password|token|answer)\\s*[:=]\\s*[^,; ]+", "$1=[REDACTED]");
        return safe.length() <= max ? safe : safe.substring(0, max);
    }
}
