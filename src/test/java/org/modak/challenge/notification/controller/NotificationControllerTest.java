package org.modak.challenge.notification.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modak.challenge.exception.InvalidEnumException;
import org.modak.challenge.exception.RateLimitException;
import org.modak.challenge.notification.controller.command.NotificationCommand;
import org.modak.challenge.notification.controller.request.NotificationRequest;
import org.modak.challenge.notification.service.NotificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
public class NotificationControllerTest {

    @Mock
    private NotificationService notificationService;

    private NotificationController notificationController;

    @BeforeEach
    void setUp() {
        this.notificationController = new NotificationController(notificationService);
    }

    @Test
    void when_incorrect_type_should_throw_exception(){
        // Given
        NotificationRequest request = new NotificationRequest(1, "type", "message");

        // When
        InvalidEnumException exception =
                Assertions.assertThrows(InvalidEnumException.class, () -> notificationController.postNotify(request));

        // Then
        Assertions.assertEquals("Invalid notification type TYPE", exception.getMessage());

        verifyNoInteractions(notificationService);
    }

    @Test
    void when_correct_request_but_service_fails_should_throw_exception(){
        // Given
        NotificationRequest request = new NotificationRequest(1, "news", "message");

        doThrow(new RateLimitException("limit exceeded"))
                .when(notificationService).send(NotificationCommand.fromRequest(request));

        // When
        RateLimitException exception =
                Assertions.assertThrows(RateLimitException.class, () -> notificationController.postNotify(request));

        // Then
        Assertions.assertEquals("limit exceeded", exception.getMessage());

        verify(notificationService, times(1)).send(NotificationCommand.fromRequest(request));
    }

    @Test
    void when_correct_request_and_service_works_should_return_ok(){
        // Given
        NotificationRequest request = new NotificationRequest(1, "news", "message");

        // When
        ResponseEntity<Void> response = notificationController.postNotify(request);

        // Then
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        verify(notificationService, times(1)).send(NotificationCommand.fromRequest(request));
    }
}
