package org.modak.challenge.notification.controller.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationRequest {
    @NotNull(message = "User id cannot be null")
    @Min(value = 1, message = "User id must be greater or equal than 1")
    private Integer userId;
    @NotBlank(message = "Notification type cannot be blank")
    private String type;
    @NotBlank(message = "Message cannot be blank")
    private String message;
}
