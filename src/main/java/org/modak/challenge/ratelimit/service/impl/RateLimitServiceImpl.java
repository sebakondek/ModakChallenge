package org.modak.challenge.ratelimit.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modak.challenge.exception.ValidatorNotImplementedException;
import org.modak.challenge.ratelimit.service.RateLimitService;
import org.modak.challenge.ratelimit.model.enums.NotificationType;
import org.modak.challenge.ratelimit.validator.Validator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class RateLimitServiceImpl implements RateLimitService {

    private static final Map<NotificationType, Validator> VALIDATORS_MAP = new HashMap<>();

    public RateLimitServiceImpl(@Qualifier("newsValidator") Validator newsValidator) {
        VALIDATORS_MAP.put(NotificationType.NEWS, newsValidator);
    }

    @Override
    public void checkRateLimit(Integer userId, NotificationType type) {
        if (VALIDATORS_MAP.containsKey(type)) {
            VALIDATORS_MAP.get(type).validate();
        } else {
            throw new ValidatorNotImplementedException(
                    String.format("%s validator not yet implemented.", type.toString().toLowerCase())
            );
        }
    }
}
