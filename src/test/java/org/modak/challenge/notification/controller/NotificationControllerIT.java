package org.modak.challenge.notification.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.modak.challenge.IntegrationTest;
import org.modak.challenge.exception.ErrorResponse;
import org.modak.challenge.notification.model.Notification;
import org.modak.challenge.notification.model.enums.NotificationType;
import org.modak.challenge.notification.repository.NotificationRepository;
import org.modak.challenge.notification.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntegrationTest
public class NotificationControllerIT {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private NotificationService notificationService;

    @Test
    void when_userId_is_null_should_throw_exception() throws Exception {
        // When
        MvcResult result = mvc.perform(post("/notify").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"type\":\"news\",\"message\":\"message\"}"))
                .andExpect(status().isBadRequest())
                .andReturn();

        // Then
        ErrorResponse errorResponse =
                objectMapper.readValue(result.getResponse().getContentAsString(), ErrorResponse.class);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, errorResponse.getHttpStatus());
        Assertions.assertEquals("User id cannot be null", errorResponse.getMessage());

        List<Notification> notifications = notificationRepository.findAll();

        Assertions.assertTrue(notifications.isEmpty());
    }

    @Test
    void when_userId_is_invalid_should_throw_exception() throws Exception {
        // When
        MvcResult result = mvc.perform(post("/notify").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"user_id\":0,\"type\":\"news\",\"message\":\"message\"}"))
                .andExpect(status().isBadRequest())
                .andReturn();

        // Then
        ErrorResponse errorResponse =
                objectMapper.readValue(result.getResponse().getContentAsString(), ErrorResponse.class);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, errorResponse.getHttpStatus());
        Assertions.assertEquals("User id must be greater or equal than 1", errorResponse.getMessage());

        List<Notification> notifications = notificationRepository.findAll();

        Assertions.assertTrue(notifications.isEmpty());
    }

    @Test
    void when_type_is_null_should_throw_exception() throws Exception {
        // When
        MvcResult result = mvc.perform(post("/notify").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"user_id\":1,\"message\":\"message\"}"))
                .andExpect(status().isBadRequest())
                .andReturn();

        // Then
        ErrorResponse errorResponse =
                objectMapper.readValue(result.getResponse().getContentAsString(), ErrorResponse.class);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, errorResponse.getHttpStatus());
        Assertions.assertEquals("Notification type cannot be blank", errorResponse.getMessage());

        List<Notification> notifications = notificationRepository.findAll();

        Assertions.assertTrue(notifications.isEmpty());
    }

    @Test
    void when_type_is_blank_should_throw_exception() throws Exception {
        // When
        MvcResult result = mvc.perform(post("/notify").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"user_id\":1,\"type\":\"\",\"message\":\"message\"}"))
                .andExpect(status().isBadRequest())
                .andReturn();

        // Then
        ErrorResponse errorResponse =
                objectMapper.readValue(result.getResponse().getContentAsString(), ErrorResponse.class);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, errorResponse.getHttpStatus());
        Assertions.assertEquals("Notification type cannot be blank", errorResponse.getMessage());

        List<Notification> notifications = notificationRepository.findAll();

        Assertions.assertTrue(notifications.isEmpty());
    }

    @Test
    void when_type_is_incorrect_should_throw_exception() throws Exception {
        // When
        MvcResult result = mvc.perform(post("/notify").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"user_id\":1,\"type\":\"asd\",\"message\":\"message\"}"))
                .andExpect(status().isBadRequest())
                .andReturn();

        // Then
        ErrorResponse errorResponse =
                objectMapper.readValue(result.getResponse().getContentAsString(), ErrorResponse.class);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, errorResponse.getHttpStatus());
        Assertions.assertEquals("Invalid notification type ASD", errorResponse.getMessage());

        List<Notification> notifications = notificationRepository.findAll();

        Assertions.assertTrue(notifications.isEmpty());
    }

    @Test
    void when_message_is_null_should_throw_exception() throws Exception {
        // When
        MvcResult result = mvc.perform(post("/notify").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"user_id\":1,\"type\":\"news\"}"))
                .andExpect(status().isBadRequest())
                .andReturn();

        // Then
        ErrorResponse errorResponse =
                objectMapper.readValue(result.getResponse().getContentAsString(), ErrorResponse.class);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, errorResponse.getHttpStatus());
        Assertions.assertEquals("Message cannot be blank", errorResponse.getMessage());

        List<Notification> notifications = notificationRepository.findAll();

        Assertions.assertTrue(notifications.isEmpty());
    }

    @Test
    void when_message_is_blank_should_throw_exception() throws Exception {
        // When
        MvcResult result = mvc.perform(post("/notify").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"user_id\":1,\"type\":\"news\",\"message\":\"\"}"))
                .andExpect(status().isBadRequest())
                .andReturn();

        // Then
        ErrorResponse errorResponse =
                objectMapper.readValue(result.getResponse().getContentAsString(), ErrorResponse.class);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, errorResponse.getHttpStatus());
        Assertions.assertEquals("Message cannot be blank", errorResponse.getMessage());

        List<Notification> notifications = notificationRepository.findAll();

        Assertions.assertTrue(notifications.isEmpty());
    }

    @Test
    void when_request_is_correct_and_limit_is_exceeded_should_throw_exception() throws Exception {
        // Given
        mvc.perform(post("/notify").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"user_id\":1,\"type\":\"news\",\"message\":\"message1\"}"))
                .andExpect(status().isOk());

        // When
        MvcResult result = mvc.perform(post("/notify").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"user_id\":1,\"type\":\"news\",\"message\":\"message2\"}"))
                .andExpect(status().isTooManyRequests())
                .andReturn();

        // Then
        ErrorResponse errorResponse =
                objectMapper.readValue(result.getResponse().getContentAsString(), ErrorResponse.class);

        Assertions.assertEquals(HttpStatus.TOO_MANY_REQUESTS, errorResponse.getHttpStatus());
        Assertions.assertEquals(
                "Rate limit of 1 per 1 Days was reached for NEWS type.",
                errorResponse.getMessage()
        );

        List<Notification> notifications = notificationRepository.findAll();

        Assertions.assertEquals(1, notifications.size());

        Assertions.assertEquals(1, notifications.get(0).getUserId());
        Assertions.assertEquals(NotificationType.NEWS, notifications.get(0).getType());
        Assertions.assertEquals("message1", notifications.get(0).getMessage());
    }

    @Test
    void when_request_is_correct_and_limit_is_not_exceeded_should_process_ok() throws Exception {
        // Given
        mvc.perform(post("/notify").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"user_id\":1,\"type\":\"status\",\"message\":\"message1\"}"))
                .andExpect(status().isOk());

        // When
        mvc.perform(post("/notify").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"user_id\":1,\"type\":\"status\",\"message\":\"message2\"}"))
                .andExpect(status().isOk());

        // Then
        List<Notification> notifications = notificationRepository.findAll();

        Assertions.assertEquals(2, notifications.size());

        Assertions.assertEquals(1, notifications.get(0).getUserId());
        Assertions.assertEquals(NotificationType.STATUS, notifications.get(0).getType());
        Assertions.assertEquals("message1", notifications.get(0).getMessage());

        Assertions.assertEquals(1, notifications.get(1).getUserId());
        Assertions.assertEquals(NotificationType.STATUS, notifications.get(1).getType());
        Assertions.assertEquals("message2", notifications.get(1).getMessage());
    }

    @Test
    void when_request_is_correct_and_limit_is_not_exceeded_but_gateway_fails_should_rollback_transaction() throws Exception {
        // When
        mvc.perform(post("/notify").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"user_id\":2,\"type\":\"status\",\"message\":\"message2\"}"))
                .andExpect(status().isInternalServerError());

        // Then
        List<Notification> notifications = notificationRepository.findAll();

        Assertions.assertEquals(0, notifications.size());
    }
}
