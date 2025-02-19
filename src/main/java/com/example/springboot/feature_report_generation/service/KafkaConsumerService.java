package com.example.springboot.feature_report_generation.service;

public interface KafkaConsumerService {
    public void processReportRequest(String reportType);

    public void generateWeeklyReport();

    public void generateMonthlyReport();

    public void generateYearlyReport();
}
