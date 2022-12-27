package org.modak.challenge.notification.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modak.challenge.notification.controller.command.NotificationCommand;
import org.modak.challenge.notification.controller.request.NotificationRequest;
import org.modak.challenge.notification.service.NotificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping("/notify")
    public ResponseEntity<Void> postNotify(@RequestBody final NotificationRequest notificationRequest) {
        NotificationCommand command = NotificationCommand.fromRequest(notificationRequest);

        this.notificationService.send(command);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
