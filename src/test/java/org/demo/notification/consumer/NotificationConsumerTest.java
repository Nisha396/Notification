package org.demo.notification.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.demo.notification.model.NotificationMessage;
import org.demo.notification.repository.NotificationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class NotificationConsumerTest {

    private NotificationConsumer consumer;
    private NotificationRepository notificationRepository;

    @BeforeEach
    void setUp() {
        consumer = new NotificationConsumer();
        notificationRepository = mock(NotificationRepository.class);
        ReflectionTestUtils.setField(consumer, "notificationRepository", notificationRepository);
    }

    @Test
    void consumeNotification_success_acknowledgesAndSaves() {
        NotificationMessage msg = new NotificationMessage();
        msg.setSender("alice");
        msg.setRecipient("bob");
        msg.setTitle("Hello");
        msg.setMessage("World");

        ConsumerRecord<String, NotificationMessage> record = new ConsumerRecord<>("topic", 0, 0L, "key", msg);
        Acknowledgment ack = mock(Acknowledgment.class);

        doNothing().when(notificationRepository).send(anyString(), anyString(), anyString(), anyString(), any());

        consumer.consumeNotification(record, ack);

        verify(notificationRepository, times(1)).send(eq("alice"), eq("bob"), eq("Hello"), eq("World"), any());
        verify(ack, times(1)).acknowledge();
    }

    @Test
    void consumeNotification_whenRepositoryThrows_doesNotAcknowledge() {
        NotificationMessage msg = new NotificationMessage();
        msg.setSender("a");
        msg.setRecipient("b");
        msg.setTitle("abc");
        msg.setMessage("message sent");
        msg.setSentAt(java.time.LocalDateTime.now());

        ConsumerRecord<String, NotificationMessage> record = new ConsumerRecord<>("topic", 0, 0L, "key", msg);
        Acknowledgment ack = mock(Acknowledgment.class);

        doThrow(new RuntimeException("db-fail")).when(notificationRepository).send(anyString(), anyString(), anyString(), anyString(), any());

        consumer.consumeNotification(record, ack);

        verify(notificationRepository, times(1)).send(anyString(), anyString(), anyString(), anyString(), any());
        verify(ack, never()).acknowledge();
    }
}