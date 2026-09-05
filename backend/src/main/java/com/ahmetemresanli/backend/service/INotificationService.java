package com.ahmetemresanli.backend.service;

import com.ahmetemresanli.backend.dto.response.NotificationResponse;
import com.ahmetemresanli.backend.enums.NotificationType;
import java.util.List;

public interface INotificationService {
    void create(Long userId, NotificationType type, String title, String message, String payload);
    List<NotificationResponse> getAll(Long userId);
    List<NotificationResponse> getUnread(Long userId);
    long unreadCount(Long userId);
    NotificationResponse markRead(Long userId, Long id);
    void markAllRead(Long userId);
}
