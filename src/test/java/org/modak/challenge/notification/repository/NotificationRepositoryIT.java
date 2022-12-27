package org.modak.challenge.notification.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.modak.challenge.IntegrationTest;
import org.modak.challenge.notification.model.Notification;
import org.modak.challenge.notification.model.enums.NotificationType;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

@IntegrationTest
public class NotificationRepositoryIT {

    @Autowired
    private NotificationRepository notificationRepository;

    @Test
    void when_countByUserIdAndTypeAndDateCreatedGreaterThan_with_no_data_should_return_0() {
        // Given
        Integer userId = 1;
        NotificationType type = NotificationType.NEWS;
        LocalDateTime dateTime = LocalDateTime.now();

        // When
        long result = notificationRepository.countByUserIdAndTypeAndDateCreatedGreaterThan(userId, type, dateTime);

        // Then
        Assertions.assertEquals(0, result);
    }

    @Test
    void when_countByUserIdAndTypeAndDateCreatedGreaterThan_with_data_but_dateTime_is_after_should_return_0() {
        // Given
        Integer userId = 1;
        NotificationType type = NotificationType.NEWS;
        LocalDateTime dateTime = LocalDateTime.now().plusHours(2);

        Notification notification = Notification.builder()
                .userId(userId)
                .type(type)
                .message("message")
                .build();

        notificationRepository.save(notification);

        // When
        long result = notificationRepository.countByUserIdAndTypeAndDateCreatedGreaterThan(userId, type, dateTime);

        // Then
        Assertions.assertEquals(0, result);
    }

    @Test
    void when_countByUserIdAndTypeAndDateCreatedGreaterThan_with_data_and_dateTime_is_before_should_return_value() {
        // Given
        Integer userId = 1;
        NotificationType type = NotificationType.NEWS;
        LocalDateTime dateTime = LocalDateTime.now().minusHours(2);

        Notification notification = Notification.builder()
                .userId(userId)
                .type(type)
                .message("message")
                .build();

        notificationRepository.save(notification);

        // When
        long result = notificationRepository.countByUserIdAndTypeAndDateCreatedGreaterThan(userId, type, dateTime);

        // Then
        Assertions.assertEquals(1, result);
    }
}
