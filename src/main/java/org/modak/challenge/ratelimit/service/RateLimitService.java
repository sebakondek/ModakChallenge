package org.modak.challenge.ratelimit.service;

import org.modak.challenge.ratelimit.model.enums.NotificationType;

public interface RateLimitService {
    void checkRateLimit(Integer userId, NotificationType type);
}
