package com.ahmetemresanli.backend.controller;

import com.ahmetemresanli.backend.dto.response.NotificationResponse;
import org.springframework.http.ResponseEntity;
import java.util.List;

public interface INotificationController {
    ResponseEntity<List<NotificationResponse>> getAll();
    ResponseEntity<List<NotificationResponse>> getUnread();
    ResponseEntity<Long> getUnreadCount();
    ResponseEntity<NotificationResponse> markRead(Long id);
    ResponseEntity<Void> markAllRead();
}
