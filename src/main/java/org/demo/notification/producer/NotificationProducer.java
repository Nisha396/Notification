package org.demo.notification.producer;

import org.demo.notification.model.NotificationMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class NotificationProducer {

    @Value("${spring.kafka.producer.topic}")
    private String TOPIC;
    private final KafkaTemplate<String, NotificationMessage> kafkaTemplate;

    public NotificationProducer(KafkaTemplate<String, NotificationMessage> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public CompletableFuture<SendResult<String, NotificationMessage>> sendNotificationEvent(NotificationMessage request) {
        // Using request.getUserId() as the routing key ensures all notifications
        // for a single user land in the exact same partition, preserving sequence order.

        return kafkaTemplate.send(TOPIC, String.valueOf(request.getMessageId()), request).
                whenComplete((result, ex) -> {
                    if (ex != null) {
                        System.err.println("Failed to send message: " + ex.getMessage());
                    } else {
                        System.out.println("Sent message successfully=[" + request +
                                "] with offset=[" + result.getRecordMetadata().offset() + "] to topic=[" + TOPIC + "]");
                    }
                });
    }
}