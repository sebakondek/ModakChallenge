package org.modak.challenge.notification.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modak.challenge.exception.RateLimitException;
import org.modak.challenge.gateway.GatewayService;
import org.modak.challenge.notification.controller.command.NotificationCommand;
import org.modak.challenge.notification.model.Notification;
import org.modak.challenge.notification.model.enums.NotificationType;
import org.modak.challenge.notification.repository.NotificationRepository;
import org.modak.challenge.notification.service.impl.NotificationServiceImpl;
import org.modak.challenge.ratelimit.service.RateLimitService;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class NotificationServiceTest {

    @Mock
    private GatewayService gatewayService;
    @Mock
    private RateLimitService rateLimitService;
    @Mock
    private NotificationRepository notificationRepository;

    private NotificationService notificationService;

    @BeforeEach
    void setUp() {
        this.notificationService = new NotificationServiceImpl(
                gatewayService,
                rateLimitService,
                notificationRepository
        );
    }

    @Test
    void when_send_but_limit_is_exceeded_throws_exception() {
        // Given
        NotificationCommand command = new NotificationCommand(1, NotificationType.NEWS, "message");

        doThrow(new RateLimitException("limit exceeded"))
                .when(rateLimitService).checkRateLimit(command.getUserId(), command.getNotificationType());

        // When
        RateLimitException exception =
                Assertions.assertThrows(RateLimitException.class, () -> notificationService.send(command));

        // Then
        Assertions.assertEquals("limit exceeded", exception.getMessage());

        verify(rateLimitService, times(1))
                .checkRateLimit(command.getUserId(), command.getNotificationType());
        verifyNoInteractions(gatewayService);
        verifyNoInteractions(notificationRepository);
    }

    @Test
    void when_send_but_gateway_service_fails_throws_exception() {
        // Given
        NotificationCommand command = new NotificationCommand(1, NotificationType.NEWS, "message");

        doThrow(new RuntimeException("Error occurred"))
                .when(gatewayService).send(command.getUserId(), command.getMessage());

        // When
        RuntimeException exception =
                Assertions.assertThrows(RuntimeException.class, () -> notificationService.send(command));

        // Then
        Assertions.assertEquals("Error occurred", exception.getMessage());

        verify(rateLimitService, times(1))
                .checkRateLimit(command.getUserId(), command.getNotificationType());
        verify(gatewayService, times(1)).send(command.getUserId(), command.getMessage());
        verifyNoInteractions(notificationRepository);
    }

    @Test
    void when_send_but_repository_fails_throws_exception() {
        // Given
        NotificationCommand command = new NotificationCommand(1, NotificationType.NEWS, "message");

        when(notificationRepository.save(Notification.fromCommand(command)))
                .thenThrow(new RuntimeException("Error occurred"));

        // When
        RuntimeException exception =
                Assertions.assertThrows(RuntimeException.class, () -> notificationService.send(command));

        // Then
        Assertions.assertEquals("Error occurred", exception.getMessage());

        Assertions.assertEquals("Error occurred", exception.getMessage());

        verify(rateLimitService, times(1))
                .checkRateLimit(command.getUserId(), command.getNotificationType());
        verify(gatewayService, times(1)).send(command.getUserId(), command.getMessage());
        verify(notificationRepository, times(1)).save(Notification.fromCommand(command));
    }

    @Test
    void when_send_and_everything_works_should_process_ok() {
        // Given
        NotificationCommand command = new NotificationCommand(1, NotificationType.NEWS, "message");

        // When
        Assertions.assertDoesNotThrow(() -> notificationService.send(command));

        // Then
        verify(rateLimitService, times(1))
                .checkRateLimit(command.getUserId(), command.getNotificationType());
        verify(gatewayService, times(1)).send(command.getUserId(), command.getMessage());
        verify(notificationRepository, times(1)).save(Notification.fromCommand(command));
    }
}
