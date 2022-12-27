package org.modak.challenge.ratelimit.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modak.challenge.exception.RateLimitException;
import org.modak.challenge.exception.ValidatorNotImplementedException;
import org.modak.challenge.notification.model.enums.NotificationType;
import org.modak.challenge.notification.repository.NotificationRepository;
import org.modak.challenge.ratelimit.service.impl.RateLimitServiceImpl;
import org.modak.challenge.ratelimit.validator.MarketingValidator;
import org.modak.challenge.ratelimit.validator.NewsValidator;
import org.modak.challenge.ratelimit.validator.NotificationValidator;
import org.modak.challenge.ratelimit.validator.StatusValidator;

import java.time.LocalDateTime;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RateLimitServiceTest {

    @Mock
    private NotificationRepository notificationRepository;

    private RateLimitService rateLimitService;

    @BeforeEach
    void setUp() {
        NotificationValidator newsValidator = new NewsValidator(notificationRepository);
        NotificationValidator statusValidator = new StatusValidator(notificationRepository);
        NotificationValidator marketingValidator = new MarketingValidator(notificationRepository);

        Map<NotificationType, NotificationValidator> validatorMap = Map.of(
                NotificationType.NEWS, newsValidator,
                NotificationType.STATUS, statusValidator,
                NotificationType.MARKETING, marketingValidator
        );

        this.rateLimitService = new RateLimitServiceImpl(validatorMap);
    }

    @Test
    void when_notificationValidators_are_not_implemented_should_throw_exception() {
        // Given
        Integer userId = 1;
        NotificationType type = NotificationType.MARKETING;

        rateLimitService = new RateLimitServiceImpl(Map.of());

        // When
        ValidatorNotImplementedException exception = Assertions.assertThrows(
                ValidatorNotImplementedException.class,
                () -> rateLimitService.checkRateLimit(userId, type)
        );

        // Then
        Assertions.assertEquals("MARKETING validator not yet implemented.", exception.getMessage());

        verifyNoInteractions(notificationRepository);
    }

    @Test
    void when_notificationValidator_NEWS_is_implemented_but_limit_is_exceeded_should_throw_exception() {
        // Given
        Integer userId = 1;
        NotificationType type = NotificationType.NEWS;

        when(notificationRepository.countByUserIdAndTypeAndDateCreatedGreaterThan(
                eq(userId), eq(type), any(LocalDateTime.class))
        ).thenReturn(1L);

        // When
        RateLimitException exception =
                Assertions.assertThrows(RateLimitException.class, () -> rateLimitService.checkRateLimit(userId, type));

        // Then
        Assertions.assertEquals("Rate limit of 1 per 1 Days was reached for NEWS type.", exception.getMessage());

        verify(notificationRepository, times(1))
                .countByUserIdAndTypeAndDateCreatedGreaterThan(eq(userId), eq(type), any(LocalDateTime.class));
    }

    @Test
    void when_notificationValidator_NEWS_is_implemented_and_limit_is_not_exceeded_should_process_ok() {
        // Given
        Integer userId = 1;
        NotificationType type = NotificationType.NEWS;

        when(notificationRepository.countByUserIdAndTypeAndDateCreatedGreaterThan(
                eq(userId), eq(type), any(LocalDateTime.class))
        ).thenReturn(0L);

        // When
        Assertions.assertDoesNotThrow(() -> rateLimitService.checkRateLimit(userId, type));

        // Then
        verify(notificationRepository, times(1))
                .countByUserIdAndTypeAndDateCreatedGreaterThan(eq(userId), eq(type), any(LocalDateTime.class));
    }

    @Test
    void when_notificationValidator_STATUS_is_implemented_but_limit_is_exceeded_should_throw_exception() {
        // Given
        Integer userId = 1;
        NotificationType type = NotificationType.STATUS;

        when(notificationRepository.countByUserIdAndTypeAndDateCreatedGreaterThan(
                eq(userId), eq(type), any(LocalDateTime.class))
        ).thenReturn(2L);

        // When
        RateLimitException exception =
                Assertions.assertThrows(RateLimitException.class, () -> rateLimitService.checkRateLimit(userId, type));

        // Then
        Assertions.assertEquals("Rate limit of 2 per 1 Minutes was reached for STATUS type.", exception.getMessage());

        verify(notificationRepository, times(1))
                .countByUserIdAndTypeAndDateCreatedGreaterThan(eq(userId), eq(type), any(LocalDateTime.class));
    }

    @Test
    void when_notificationValidator_STATUS_is_implemented_and_limit_is_not_exceeded_should_process_ok() {
        // Given
        Integer userId = 1;
        NotificationType type = NotificationType.STATUS;

        when(notificationRepository.countByUserIdAndTypeAndDateCreatedGreaterThan(
                eq(userId), eq(type), any(LocalDateTime.class))
        ).thenReturn(0L);

        // When
        Assertions.assertDoesNotThrow(() -> rateLimitService.checkRateLimit(userId, type));

        // Then
        verify(notificationRepository, times(1))
                .countByUserIdAndTypeAndDateCreatedGreaterThan(eq(userId), eq(type), any(LocalDateTime.class));
    }

    @Test
    void when_notificationValidator_MARKETING_is_implemented_but_limit_is_exceeded_should_throw_exception() {
        // Given
        Integer userId = 1;
        NotificationType type = NotificationType.MARKETING;

        when(notificationRepository.countByUserIdAndTypeAndDateCreatedGreaterThan(
                eq(userId), eq(type), any(LocalDateTime.class))
        ).thenReturn(3L);

        // When
        RateLimitException exception =
                Assertions.assertThrows(RateLimitException.class, () -> rateLimitService.checkRateLimit(userId, type));

        // Then
        Assertions.assertEquals("Rate limit of 3 per 1 Hours was reached for MARKETING type.", exception.getMessage());

        verify(notificationRepository, times(1))
                .countByUserIdAndTypeAndDateCreatedGreaterThan(eq(userId), eq(type), any(LocalDateTime.class));
    }

    @Test
    void when_notificationValidator_MARKETING_is_implemented_and_limit_is_not_exceeded_should_process_ok() {
        // Given
        Integer userId = 1;
        NotificationType type = NotificationType.MARKETING;

        when(notificationRepository.countByUserIdAndTypeAndDateCreatedGreaterThan(
                eq(userId), eq(type), any(LocalDateTime.class))
        ).thenReturn(0L);

        // When
        Assertions.assertDoesNotThrow(() -> rateLimitService.checkRateLimit(userId, type));

        // Then
        verify(notificationRepository, times(1))
                .countByUserIdAndTypeAndDateCreatedGreaterThan(eq(userId), eq(type), any(LocalDateTime.class));
    }
}
