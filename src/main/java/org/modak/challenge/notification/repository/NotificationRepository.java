package org.modak.challenge.notification.repository;

import org.modak.challenge.notification.model.Notification;
import org.modak.challenge.notification.model.enums.NotificationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    long countByUserIdAndTypeAndDateCreatedGreaterThan(
            Integer userId,
            NotificationType type,
            LocalDateTime dateTime
    );
}
