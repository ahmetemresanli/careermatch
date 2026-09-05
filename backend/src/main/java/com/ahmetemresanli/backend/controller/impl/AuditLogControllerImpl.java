package com.ahmetemresanli.backend.controller.impl;

import com.ahmetemresanli.backend.controller.IAuditLogController;
import com.ahmetemresanli.backend.dto.response.AuditLogResponse;
import com.ahmetemresanli.backend.service.IAuditLogService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/api/audit-logs")
public class AuditLogControllerImpl implements IAuditLogController {
    private final IAuditLogService service; public AuditLogControllerImpl(IAuditLogService service) { this.service = service; }
    @Override @GetMapping public ResponseEntity<Page<AuditLogResponse>> list(
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(service.list(page, size));
    }
}
