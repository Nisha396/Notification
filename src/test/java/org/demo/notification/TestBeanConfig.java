package org.demo.notification;

import org.demo.notification.model.NotificationMessage;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;

@TestConfiguration
public class TestBeanConfig {

    @Bean
    public KafkaTemplate<String, NotificationMessage> kafkaTemplate() {
        // Provide a Mockito mock so tests that accidentally load components depending on KafkaTemplate won't fail
        return Mockito.mock(KafkaTemplate.class);
    }
}
