package org.modak.challenge.ratelimit.validator;

import org.modak.challenge.notification.repository.NotificationRepository;
import org.springframework.stereotype.Component;

import java.time.temporal.ChronoUnit;

@Component("newsValidator")
public class NewsValidator extends NotificationValidator {

    private static final ChronoUnit TEMPORAL_UNIT = ChronoUnit.DAYS;
    private static final long TIME_UNIT = 1;
    private static final long MAX_COUNT_PER_TIME_WINDOW = 1;

    public NewsValidator(NotificationRepository notificationRepository) {
        super(notificationRepository, TIME_UNIT, TEMPORAL_UNIT, MAX_COUNT_PER_TIME_WINDOW);
    }
}
