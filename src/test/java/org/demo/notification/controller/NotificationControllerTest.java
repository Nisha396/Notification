package org.demo.notification.controller;

import org.apache.kafka.clients.producer.RecordMetadata;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.demo.notification.model.NotificationMessage;
import org.demo.notification.producer.NotificationProducer;
import org.demo.notification.service.NotificationService;

import org.demo.notification.consumer.NotificationConsumer;
import org.springframework.kafka.support.SendResult;

import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationControllerTest {

    @Mock
    private NotificationService notificationService;

    @Mock
    private NotificationProducer notificationProducer;

    @Mock
    private NotificationConsumer notificationConsumer;

    @InjectMocks
    private NotificationController notificationController;

    @Test
    void sendNotification_delegatesToService() {
        NotificationMessage msg = new NotificationMessage();
        when(notificationService.sendNotification(msg)).thenReturn("Notification sent!");

        String resp = notificationController.sendNotification(msg);
        assertEquals("Notification sent!", resp);
        verify(notificationService, times(1)).sendNotification(msg);
    }

    @Test
    void sendNotificationMessage_success_returnsTopicMessage() throws Exception {
        NotificationMessage msg = new NotificationMessage();
        SendResult<String, NotificationMessage> sendResult = mock(SendResult.class);
        RecordMetadata metadata = mock(RecordMetadata.class);
        when(metadata.topic()).thenReturn("test-topic");
        when(sendResult.getRecordMetadata()).thenReturn(metadata);

        when(notificationProducer.sendNotificationEvent(msg)).thenReturn(CompletableFuture.completedFuture(sendResult));

        String resp = notificationController.sendNotificationMessage(msg);
        assertTrue(resp.contains("test-topic"));
    }

    @Test
    void sendNotificationMessage_failure_returnsErrorMessage() {
        NotificationMessage msg = new NotificationMessage();
        when(notificationProducer.sendNotificationEvent(msg)).thenReturn(CompletableFuture.failedFuture(new RuntimeException("boom")));

        String resp = notificationController.sendNotificationMessage(msg);
        assertTrue(resp.contains("Failed to send notification"));
    }
}