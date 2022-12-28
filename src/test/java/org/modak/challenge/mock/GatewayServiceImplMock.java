package org.modak.challenge.mock;

import lombok.extern.slf4j.Slf4j;
import org.modak.challenge.gateway.GatewayService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@Primary
@ConditionalOnProperty(name = "isTest", havingValue = "true")
public class GatewayServiceImplMock implements GatewayService {

    /**
     * Mocked Gateway implementation to facilitate integration tests
     * @param userId
     * @param message
     */
    @Override
    public void send(Integer userId, String message) {
        if (userId == 2) {
            throw new RuntimeException("Gateway provider error.");
        }

        log.info("Sending message '{}' to user {}", message, userId);
    }
}
