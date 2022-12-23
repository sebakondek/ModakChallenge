package org.pedidosya.kata.health.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class HealthControllerTest {

    private HealthController healthController;

    @BeforeEach
    void setUp() {
        this.healthController = new HealthController();
    }

    @Test
    void when_checkHealth_must_respond_ok() {
        // When
        ResponseEntity<Void> response = this.healthController.checkHealth();

        // Then
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
