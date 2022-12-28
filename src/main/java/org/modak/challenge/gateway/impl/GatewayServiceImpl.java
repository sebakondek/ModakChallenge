package org.modak.challenge.gateway.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modak.challenge.gateway.GatewayService;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class GatewayServiceImpl implements GatewayService {

    /**
     * Gateway implementation that sends notifications to users
     * @param userId
     * @param message
     */
    @Override
    public void send(Integer userId, String message) {
        log.info("Sending message '{}' to user {}", message, userId);
    }
}
