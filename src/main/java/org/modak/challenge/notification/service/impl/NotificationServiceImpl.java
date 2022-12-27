package org.modak.challenge.notification.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.modak.challenge.gateway.GatewayService;
import org.modak.challenge.notification.service.NotificationService;
import org.modak.challenge.ratelimit.service.RateLimitService;
import org.modak.challenge.ratelimit.model.enums.NotificationType;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    private final GatewayService gatewayService;
    private final RateLimitService rateLimitService;

    public NotificationServiceImpl(GatewayService gatewayService, RateLimitService rateLimitService) {
        this.gatewayService = gatewayService;
        this.rateLimitService = rateLimitService;
    }

    @Override
    public void send(NotificationType type, Integer userId, String message) {
        this.rateLimitService.checkRateLimit(userId, type);

        this.gatewayService.send(userId, message);
    }
}
