package org.demo.notification.service;

import org.demo.notification.model.NotificationMessage;
import org.demo.notification.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    public String sendNotification(NotificationMessage message) {

        if(message.getMessageId() != null) {
            NotificationMessage existingMessage = notificationRepository.findById
                    (message.getMessageId()).orElse(null);
            if (existingMessage != null) {
                return "Notification with ID " + message.getMessageId() + " already exists.";
            }
        }
            Long messageId = notificationRepository.send
                    (message.getSender(), message.getRecipient(), message.getTitle(),
                            message.getMessage(), LocalDateTime.now());
        return "Notification sent with messageId generated as " + messageId;
    }

    public NotificationMessage getMessage(long messageId) {
        return notificationRepository.findById(messageId).orElse(null);
    }
}
