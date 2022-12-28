package org.modak.challenge.notification.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modak.challenge.gateway.GatewayService;
import org.modak.challenge.notification.controller.command.NotificationCommand;
import org.modak.challenge.notification.model.Notification;
import org.modak.challenge.notification.repository.NotificationRepository;
import org.modak.challenge.notification.service.NotificationService;
import org.modak.challenge.ratelimit.service.RateLimitService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final GatewayService gatewayService;
    private final RateLimitService rateLimitService;
    private final NotificationRepository notificationRepository;

    /**
     * Service in charge of distributing different responsibilities.
     *
     * Checks if userId and notificationType are not rate limited.
     * Sends the notification through the GatewayService.
     * Finally, persists the entity in the database.
     * @param command
     */
    @Transactional
    @Override
    public void send(NotificationCommand command) {
        this.rateLimitService.checkRateLimit(command.getUserId(), command.getNotificationType());

        this.notificationRepository.save(Notification.fromCommand(command));

        this.gatewayService.send(command.getUserId(), command.getMessage());
    }
}
