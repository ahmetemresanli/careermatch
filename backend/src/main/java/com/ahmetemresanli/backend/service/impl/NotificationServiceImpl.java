package com.ahmetemresanli.backend.service.impl;

import com.ahmetemresanli.backend.dto.response.NotificationResponse;
import com.ahmetemresanli.backend.entity.Notification;
import com.ahmetemresanli.backend.entity.User;
import com.ahmetemresanli.backend.enums.NotificationType;
import com.ahmetemresanli.backend.exception.ResourceNotFoundException;
import com.ahmetemresanli.backend.repository.NotificationRepository;
import com.ahmetemresanli.backend.repository.UserRepository;
import com.ahmetemresanli.backend.service.INotificationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationServiceImpl implements INotificationService {
    private final NotificationRepository notifications; private final UserRepository users;
    public NotificationServiceImpl(NotificationRepository notifications, UserRepository users) {
        this.notifications = notifications; this.users = users;
    }
    @Override public void create(Long userId, NotificationType type, String title, String message, String payload) {
        User user = users.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Notification n = new Notification(); n.setUser(user); n.setType(type); n.setTitle(title);
        n.setMessage(message); n.setPayload(payload); notifications.save(n);
    }
    @Override public List<NotificationResponse> getAll(Long userId) {
        return notifications.findByUserIdOrderByCreatedAtDesc(userId).stream().map(this::map).toList();
    }
    @Override public List<NotificationResponse> getUnread(Long userId) {
        return notifications.findByUserIdAndReadFalseOrderByCreatedAtDesc(userId).stream().map(this::map).toList();
    }
    @Override public long unreadCount(Long userId) { return notifications.countByUserIdAndReadFalse(userId); }
    @Override @Transactional public NotificationResponse markRead(Long userId, Long id) {
        Notification n = owned(userId, id); if (!n.isRead()) { n.setRead(true); n.setReadAt(LocalDateTime.now()); }
        return map(notifications.save(n));
    }
    @Override @Transactional public void markAllRead(Long userId) {
        notifications.findByUserIdAndReadFalseOrderByCreatedAtDesc(userId).forEach(n -> {
            n.setRead(true); n.setReadAt(LocalDateTime.now());
        });
    }
    private Notification owned(Long userId, Long id) {
        return notifications.findById(id).filter(n -> n.getUser().getId().equals(userId))
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found"));
    }
    private NotificationResponse map(Notification n) {
        return new NotificationResponse(n.getId(), n.getType(), n.getTitle(), n.getMessage(), n.getPayload(),
                n.isRead(), n.getReadAt(), n.getCreatedAt());
    }
}
