package org.modak.challenge.gateway.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modak.challenge.gateway.GatewayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class GatewayServiceImpl implements GatewayService {
    private static final Logger LOGGER = LoggerFactory.getLogger(GatewayServiceImpl.class);

    @Override
    public void send(Integer userId, String message) {
        LOGGER.info("Sending message '{}' to user {}", message, userId);
    }
}
