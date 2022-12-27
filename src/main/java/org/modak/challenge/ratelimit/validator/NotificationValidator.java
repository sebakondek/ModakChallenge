package org.modak.challenge.ratelimit.validator;

import lombok.RequiredArgsConstructor;
import org.modak.challenge.exception.RateLimitException;
import org.modak.challenge.notification.model.enums.NotificationType;
import org.modak.challenge.notification.repository.NotificationRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@RequiredArgsConstructor
public abstract class NotificationValidator {

    private final NotificationRepository notificationRepository;
    private final long timeUnit;
    private final ChronoUnit temporalUnit;
    private final long maxCountPerTimeWindow;

    public void validate(Integer userId, NotificationType type) {
        LocalDateTime startDate = LocalDateTime.now().minus(timeUnit, temporalUnit);

        long countResult =
                notificationRepository.countByUserIdAndTypeAndDateCreatedGreaterThan(userId, type, startDate);

        if (maxCountPerTimeWindow <= countResult) {
            throw new RateLimitException(
                    String.format("Rate limit of %s per %s %s was reached for %s type.",
                            maxCountPerTimeWindow, timeUnit, temporalUnit, type)
            );
        }
    }
}
