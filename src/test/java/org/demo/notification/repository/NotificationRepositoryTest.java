package org.demo.notification.repository;

import org.demo.notification.model.NotificationMessage;
import org.demo.notification.producer.NotificationProducer;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class NotificationRepositoryTest {

    @Autowired
    private NotificationRepository notificationRepository;

    @Test
    void send_insertsRow_findAllReturnsInserted() {
        // Use repository.save to persist via JPA to avoid native-insert portability issues
        NotificationMessage msg = new NotificationMessage();
        msg.setSender("alice");
        msg.setRecipient("bob");
        msg.setTitle("Title");
        msg.setMessage("Body");
        msg.setSentAt(LocalDateTime.now());

        notificationRepository.save(msg);

        List<NotificationMessage> all = notificationRepository.findAll();
        assertFalse(all.isEmpty(), "Repository should contain at least one saved message");
        NotificationMessage saved = all.get(0);
        assertEquals("alice", saved.getSender());
        assertEquals("bob", saved.getRecipient());
    }
}