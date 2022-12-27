package org.modak.challenge.ratelimit.validator;

import org.modak.challenge.notification.repository.NotificationRepository;
import org.springframework.stereotype.Component;

import java.time.temporal.ChronoUnit;

@Component("statusValidator")
public class StatusValidator extends NotificationValidator {

    private static final ChronoUnit TEMPORAL_UNIT = ChronoUnit.MINUTES;
    private static final long TIME_UNIT = 1;
    private static final long MAX_COUNT_PER_TIME_WINDOW = 2;

    public StatusValidator(NotificationRepository notificationRepository) {
        super(notificationRepository, TIME_UNIT, TEMPORAL_UNIT, MAX_COUNT_PER_TIME_WINDOW);
    }
}
