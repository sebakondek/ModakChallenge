package org.modak.challenge.ratelimit.validator;

import org.modak.challenge.notification.model.enums.NotificationType;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ValidatorsConfig {

    private final Map<NotificationType, NotificationValidator> validatorsMap = new HashMap<>();

    public ValidatorsConfig(
            @Qualifier("newsValidator") NotificationValidator newsValidator,
            @Qualifier("statusValidator") NotificationValidator statusValidator,
            @Qualifier("marketingValidator") NotificationValidator marketingValidator
    ) {
        validatorsMap.put(NotificationType.NEWS, newsValidator);
        validatorsMap.put(NotificationType.STATUS, statusValidator);
        validatorsMap.put(NotificationType.MARKETING, marketingValidator);
    }

    @Bean(name = "validatorsMap")
    public Map<NotificationType, NotificationValidator> getValidatorsMap() {
        return validatorsMap;
    }
}
