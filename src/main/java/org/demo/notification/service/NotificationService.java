package org.demo.notification.service;

import org.demo.notification.model.NotificationMessage;
import org.demo.notification.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    public String sendNotification(NotificationMessage message) {

        NotificationMessage existingMessage = notificationRepository.findById
                (message.getMessageId()).orElse(null);
        if (existingMessage != null) {
            return "Notification with ID " + message.getMessageId() + " already exists.";
        }
        notificationRepository.send
                (message.getSender(), message.getRecipient(), message.getTitle(),
                        message.getMessage(), message.getSentAt());
        return "Notification sent!";
    }

    public NotificationMessage getMessage(long messageId) {
        return notificationRepository.findById(messageId).orElse(null);
    }
}
