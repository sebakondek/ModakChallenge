package org.pedidosya.kata.health.controller;

import org.junit.jupiter.api.Test;
import org.pedidosya.kata.IntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntegrationTest
public class HealthControllerIT {

    @Autowired
    private MockMvc mvc;

    @Test
    void when_health_should_respond_ok() throws Exception {
        mvc.perform(get("/health")).andExpect(status().isOk());
    }
}
