package org.modak.challenge.notification.model.enums;

import org.modak.challenge.exception.InvalidEnumException;

public enum NotificationType {
    STATUS,
    NEWS,
    MARKETING;

    public static NotificationType getEnum(String value) {
        try {
            return NotificationType.valueOf(value);
        } catch (IllegalArgumentException ex) {
            throw new InvalidEnumException(
                    String.format("Invalid notification type %s", value)
            );
        }
    }
}
