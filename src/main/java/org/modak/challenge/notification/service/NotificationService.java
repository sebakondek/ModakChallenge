package org.modak.challenge.notification.service;

import org.modak.challenge.notification.controller.command.NotificationCommand;

public interface NotificationService {
    void send(NotificationCommand command);
}
