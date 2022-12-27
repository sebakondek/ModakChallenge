package org.modak.challenge.notification.service;

import org.modak.challenge.ratelimit.model.enums.NotificationType;

public interface NotificationService {
    void send(NotificationType type, Integer userId, String message);
}
