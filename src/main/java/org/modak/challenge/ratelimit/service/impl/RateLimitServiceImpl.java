package org.modak.challenge.ratelimit.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.modak.challenge.exception.ValidatorNotImplementedException;
import org.modak.challenge.notification.model.enums.NotificationType;
import org.modak.challenge.ratelimit.service.RateLimitService;
import org.modak.challenge.ratelimit.validator.NotificationValidator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class RateLimitServiceImpl implements RateLimitService {

    private static final Map<NotificationType, NotificationValidator> VALIDATORS_MAP = new HashMap<>();

    public RateLimitServiceImpl(@Qualifier("newsValidator") NotificationValidator newsValidator) {
        VALIDATORS_MAP.put(NotificationType.NEWS, newsValidator);
    }

    @Override
    public void checkRateLimit(Integer userId, NotificationType type) {
        if (VALIDATORS_MAP.containsKey(type)) {
            VALIDATORS_MAP.get(type).validate(userId, type);
        } else {
            throw new ValidatorNotImplementedException(
                    String.format("%s validator not yet implemented.", type.toString().toLowerCase())
            );
        }
    }
}
