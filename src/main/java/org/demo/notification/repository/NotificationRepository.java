package org.demo.notification.repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.demo.notification.model.NotificationMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationMessage, Long> {

    @Transactional
    @Query(
            value = "INSERT INTO notification_message (sender,recipient, title, message, sent_at) VALUES (:sender, :recipient, :title, :message, :sentAt)" +
                    "RETURNING message_id",
            nativeQuery = true
    )
    Long send(
            @Param("sender") String sender,
            @Param("recipient") String recipient,
            @Param("title") String title,
            @Param("message") String message,
            @Param("sentAt") LocalDateTime sentAt
    );
}
