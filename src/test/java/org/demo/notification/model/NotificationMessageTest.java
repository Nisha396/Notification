package org.demo.notification.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class NotificationMessageTest {

    @Test
    void lombok_gettersSetters_work() {
        NotificationMessage msg = new NotificationMessage();
        msg.setSender("alice");
        msg.setRecipient("bob");
        msg.setTitle("Hi");
        msg.setMessage("Hello");
        msg.setSentAt(LocalDateTime.now());

        assertEquals("alice", msg.getSender());
        assertEquals("bob", msg.getRecipient());
        assertEquals("Hi", msg.getTitle());
        assertEquals("Hello", msg.getMessage());
        assertNotNull(msg.getSentAt());
    }
}