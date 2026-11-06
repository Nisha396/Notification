package org.demo.notification.service;

import org.demo.notification.model.NotificationMessage;
import org.demo.notification.repository.NotificationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {

    @Mock
    private NotificationRepository notificationRepository;

    @InjectMocks
    private NotificationService notificationService;

    @Test
    void sendNotification_whenExists_returnsAlreadyExists() {
        NotificationMessage msg = new NotificationMessage();
        msg.setMessageId(1L);

        when(notificationRepository.findById(1L)).thenReturn(Optional.of(msg));

        String result = notificationService.sendNotification(msg);
        assertTrue(result.contains("already exists"));
        verify(notificationRepository, never()).send(anyString(), anyString(), anyString(), anyString(), any());
    }

    @Test
    void sendNotification_whenNotExists_savesAndReturnsSent() {
        NotificationMessage msg = new NotificationMessage();
        msg.setMessageId(2L);
        msg.setSender("alice");
        msg.setRecipient("bob");
        msg.setTitle("Hi");
        msg.setMessage("Hello");
        msg.setSentAt(LocalDateTime.now());

        when(notificationRepository.findById(2L)).thenReturn(Optional.empty());

        String result = notificationService.sendNotification(msg);
        assertEquals("Notification sent!", result);
        verify(notificationRepository, times(1)).send(eq("alice"), eq("bob"), eq("Hi"), eq("Hello"), any(LocalDateTime.class));
    }

    @Test
    void getMessage_returnsRepositoryValue() {
        NotificationMessage msg = new NotificationMessage();
        msg.setMessageId(3L);
        when(notificationRepository.findById(3L)).thenReturn(Optional.of(msg));

        NotificationMessage found = notificationService.getMessage(3L);
        assertNotNull(found);
        assertEquals(3L, found.getMessageId());
    }
}