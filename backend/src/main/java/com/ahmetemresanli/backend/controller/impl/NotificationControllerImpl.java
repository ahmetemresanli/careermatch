package com.ahmetemresanli.backend.controller.impl;

import com.ahmetemresanli.backend.controller.INotificationController;
import com.ahmetemresanli.backend.dto.response.NotificationResponse;
import com.ahmetemresanli.backend.security.AccessControlService;
import com.ahmetemresanli.backend.service.INotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/api/notifications")
public class NotificationControllerImpl implements INotificationController {
    private final INotificationService service; private final AccessControlService access;
    public NotificationControllerImpl(INotificationService service, AccessControlService access) { this.service = service; this.access = access; }
    @Override @GetMapping public ResponseEntity<List<NotificationResponse>> getAll() { return ResponseEntity.ok(service.getAll(access.currentUserId())); }
    @Override @GetMapping("/unread") public ResponseEntity<List<NotificationResponse>> getUnread() { return ResponseEntity.ok(service.getUnread(access.currentUserId())); }
    @Override @GetMapping("/unread-count") public ResponseEntity<Long> getUnreadCount() { return ResponseEntity.ok(service.unreadCount(access.currentUserId())); }
    @Override @PutMapping("/{id}/read") public ResponseEntity<NotificationResponse> markRead(@PathVariable Long id) { return ResponseEntity.ok(service.markRead(access.currentUserId(), id)); }
    @Override @PutMapping("/read-all") public ResponseEntity<Void> markAllRead() { service.markAllRead(access.currentUserId()); return ResponseEntity.noContent().build(); }
}
