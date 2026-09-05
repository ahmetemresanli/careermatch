package com.ahmetemresanli.backend.controller;

import com.ahmetemresanli.backend.dto.response.AuditLogResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

public interface IAuditLogController { ResponseEntity<Page<AuditLogResponse>> list(int page, int size); }
