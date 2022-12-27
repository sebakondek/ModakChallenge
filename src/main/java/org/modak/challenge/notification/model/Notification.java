package org.modak.challenge.notification.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.modak.challenge.notification.controller.command.NotificationCommand;
import org.modak.challenge.notification.model.enums.NotificationType;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "notifications",
        indexes = @Index(name = "idx_notification_userId_type", columnList = "user_id, type")
)
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private NotificationType type;

    @Column(name = "message", nullable = false)
    private String message;

    @CreationTimestamp
    @Column(name = "date_created", updatable = false)
    private LocalDateTime dateCreated;

    private Notification(Integer userId, NotificationType type, String message) {
        this.userId = userId;
        this.type = type;
        this.message = message;
    }

    public static Notification fromCommand(NotificationCommand command) {
        return new Notification(
                command.getUserId(),
                command.getNotificationType(),
                command.getMessage()
        );
    }
}
