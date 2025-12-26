package org.demo.notification.controller;

import org.demo.notification.model.NotificationMessage;
import org.demo.notification.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping("/send")
    public String sendNotification(@RequestBody NotificationMessage notificationMessage) {
        return notificationService.sendNotification(notificationMessage);
    }

    @GetMapping("/getMessage/{messageId}")
    public String getMessage(@PathVariable("messageId") long messageId) {
        NotificationMessage message = notificationService.getMessage(messageId);
        if (message != null) {
            return "Message found with Id: " + message.getMessageId();
        } else {
            return "Message with Id " + messageId + " not found.";
        }
    }
}
