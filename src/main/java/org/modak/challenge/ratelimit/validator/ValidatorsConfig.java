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

    /**
     * Constructor to add validators into the map.
     * New entries require a valid NotificationType and an existing
     * NotificationValidator implementation.
     *
     * Note: If a key is put more than once, last validator will remain.
     * @param newsValidator
     * @param statusValidator
     * @param marketingValidator
     */
    public ValidatorsConfig(
            @Qualifier("newsValidator") NotificationValidator newsValidator,
            @Qualifier("statusValidator") NotificationValidator statusValidator,
            @Qualifier("marketingValidator") NotificationValidator marketingValidator
    ) {
        validatorsMap.put(NotificationType.NEWS, newsValidator);
        validatorsMap.put(NotificationType.STATUS, statusValidator);
        validatorsMap.put(NotificationType.MARKETING, marketingValidator);
    }

    /**
     * Bean to inject the map where necessary.
     * @return
     */
    @Bean(name = "validatorsMap")
    public Map<NotificationType, NotificationValidator> getValidatorsMap() {
        return validatorsMap;
    }
}
