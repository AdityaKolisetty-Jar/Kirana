package com.example.springboot.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {

    /**
     * for creating a new topic
     *
     * @return
     */
    @Bean
    public NewTopic reportTopic() {
        return new NewTopic("report-generation", 1, (short) 1);
    }
}
