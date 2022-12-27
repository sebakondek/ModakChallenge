package org.modak.challenge.ratelimit.validator;

import org.modak.challenge.notification.repository.NotificationRepository;
import org.springframework.stereotype.Component;

import java.time.temporal.ChronoUnit;

@Component("marketingValidator")
public class MarketingValidator extends NotificationValidator {

    private static final ChronoUnit TEMPORAL_UNIT = ChronoUnit.HOURS;
    private static final long TIME_UNIT = 1;
    private static final long MAX_COUNT_PER_TIME_WINDOW = 3;

    public MarketingValidator(NotificationRepository notificationRepository) {
        super(notificationRepository, TIME_UNIT, TEMPORAL_UNIT, MAX_COUNT_PER_TIME_WINDOW);
    }
}
