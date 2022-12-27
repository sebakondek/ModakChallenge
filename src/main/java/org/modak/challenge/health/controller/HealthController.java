package org.modak.challenge.health.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
public class HealthController {

    private static final Logger logger = LoggerFactory.getLogger(HealthController.class);

    /**
     * Health check for container purposes
     * @return
     */
    @GetMapping
    public ResponseEntity<Void> checkHealth() {
        logger.info("I'm healthy!");

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
