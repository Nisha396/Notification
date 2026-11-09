package org.demo.notification.consumer;

import lombok.extern.slf4j.Slf4j;
import org.demo.notification.model.NotificationMessage;
import org.demo.notification.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class NotificationConsumer {

    @Autowired
    private NotificationRepository notificationRepository;

    @KafkaListener(topics = "notification-topic", groupId = "notification-worker-group", concurrency = "3")
    public void consumeNotification(NotificationMessage message, Acknowledgment ack) {
        try {
            System.out.println(">>> RECEIVED BY SPRING BOOT LISTENER: " + message);

            // Execute the business logic (e.g., Calling Email/SMS Gateway gateways)
            Long messageId= processDelivery(message);
            log.info("MessageId saved in DB is {}", messageId);

            // Manually commit offset ONLY after successful execution to guarantee at-least-once delivery
            ack.acknowledge();

        } catch (Exception e) {
            // In interviews, explain that you would route failures to a Dead Letter Queue (DLQ) here
            System.err.println("Failed processing message: " + e.getMessage());
        }
    }

    private Long processDelivery(NotificationMessage message) {
        return notificationRepository.send
                (message.getSender(), message.getRecipient(), message.getTitle(),
                        message.getMessage(), LocalDateTime.now());
    }
}
