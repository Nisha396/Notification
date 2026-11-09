package org.demo.notification.controller;

import org.demo.notification.consumer.NotificationConsumer;
import org.demo.notification.model.NotificationMessage;
import org.demo.notification.producer.NotificationProducer;
import org.demo.notification.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private NotificationProducer notificationProducer;

    @Autowired
    private NotificationConsumer notificationConsumer;

    @PostMapping("/api/v1/send")
    public ResponseEntity<String> sendNotification(@RequestBody NotificationMessage notificationMessage) {
        try {
            String response = notificationService.sendNotification(notificationMessage);
            return  ResponseEntity.ok(response);
        }
        catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error sending notification: " + e.getMessage());
        }
    }

    @PostMapping("/api/v1/notify")
    public String sendNotificationMessage(@RequestBody NotificationMessage notificationMessage) {
        return notificationProducer.sendNotificationEvent(notificationMessage)
                .thenApply(result -> "Notification sent to Kafka topic: " + result.getRecordMetadata().topic())
                .exceptionally(ex -> "Failed to send notification: " + ex.getMessage())
                .join();
    }


    @GetMapping("/api/v1/status/{messageId}")
    public String getMessage(@PathVariable("messageId") long messageId) {
        NotificationMessage message = notificationService.getMessage(messageId);
        if (message != null) {
            return "Message found with Id: " + message.getMessageId() + " and content: " + message;
        } else {
            return "Message with Id " + messageId + " not found.";
        }
    }
}
