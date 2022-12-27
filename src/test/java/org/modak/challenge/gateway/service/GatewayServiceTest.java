package org.modak.challenge.gateway.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modak.challenge.gateway.GatewayService;
import org.modak.challenge.gateway.impl.GatewayServiceImpl;

public class GatewayServiceTest {

    private GatewayService gatewayService;

    @BeforeEach
    void setUp() {
        this.gatewayService = new GatewayServiceImpl();
    }

    @Test
    void when_send_should_process_without_exception() {
        // When
        Assertions.assertDoesNotThrow(() -> gatewayService.send(1, "message"));
    }
}
