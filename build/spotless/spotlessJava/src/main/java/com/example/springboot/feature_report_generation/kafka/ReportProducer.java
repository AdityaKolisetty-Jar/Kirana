package com.example.springboot.feature_report_generation.kafka;

import com.example.springboot.feature_report_generation.entity.Report;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ReportProducer {
    private final KafkaTemplate<String, Report> kafkaTemplate;

    public ReportProducer(KafkaTemplate<String, Report> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendReportRequest(Report report) {
        kafkaTemplate.send("report-topic", report);
    }
}
