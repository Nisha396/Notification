package org.demo.notification.producer;

import org.demo.notification.model.NotificationMessage;
import org.junit.jupiter.api.Test;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.concurrent.CompletableFuture;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class NotificationProducerTest {

    @Test
    void sendNotificationEvent_invokesKafkaTemplate() {
        @SuppressWarnings("unchecked")
        KafkaTemplate<String, NotificationMessage> kafkaTemplate = mock(KafkaTemplate.class);
        NotificationProducer producer = new NotificationProducer(kafkaTemplate);
        ReflectionTestUtils.setField(producer, "TOPIC", "unit-test-topic");

        NotificationMessage msg = new NotificationMessage();
        msg.setMessageId(10L);

        when(kafkaTemplate.send(eq("unit-test-topic"), eq(String.valueOf(10L)), eq(msg)))
                .thenReturn(CompletableFuture.completedFuture(null));

        CompletableFuture<?> future = producer.sendNotificationEvent(msg);
        assertNotNull(future);
        verify(kafkaTemplate, times(1)).send("unit-test-topic", String.valueOf(10L), msg);
    }
}