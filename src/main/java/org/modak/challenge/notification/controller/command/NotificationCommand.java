package org.modak.challenge.notification.controller.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modak.challenge.notification.controller.request.NotificationRequest;
import org.modak.challenge.notification.model.enums.NotificationType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationCommand {
    private Integer userId;
    private NotificationType notificationType;
    private String message;

    public static NotificationCommand fromRequest(NotificationRequest request) {
        return new NotificationCommand(
                request.getUserId(),
                NotificationType.getEnum(request.getType().toUpperCase()),
                request.getMessage()
        );
    }
}
