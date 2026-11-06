package org.demo.notification.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.demo.notification.model.NotificationMessage;
import org.demo.notification.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

@Service
public class NotificationConsumer {

    @Autowired
    private NotificationRepository notificationRepository;

    @KafkaListener(topics = "${spring.kafka.producer.topic}", groupId = "notification-worker-group", concurrency = "3")
    public void consumeNotification(ConsumerRecord<String, NotificationMessage> record, Acknowledgment ack) {
        try {
            NotificationMessage request = record.value();

            // Execute the business logic (e.g., Calling Email/SMS Gateway gateways)
            processDelivery(request);

            // Manually commit offset ONLY after successful execution to guarantee at-least-once delivery
            ack.acknowledge();

        } catch (Exception e) {
            // In interviews, explain that you would route failures to a Dead Letter Queue (DLQ) here
            System.err.println("Failed processing message: " + e.getMessage());
        }
    }

    private void processDelivery(NotificationMessage message) {
        notificationRepository.send
                (message.getSender(), message.getRecipient(), message.getTitle(),
                        message.getMessage(), message.getSentAt());
    }
}
