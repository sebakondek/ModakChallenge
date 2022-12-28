package org.modak.challenge.health.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/health")
public class HealthController {

    /**
     * Health check for container purposes
     * @return
     */
    @GetMapping
    public ResponseEntity<Void> checkHealth() {
        log.info("I'm healthy!");

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
