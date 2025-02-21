package com.example.springboot.feature_report_generation.services;

public interface KafkaConsumerService {

    /**
     * processes report requests and listens to messages passed in kafka
     *
     * @param reportType
     */
    public void processReportRequest(String reportType);

    /** generates a weekly report */
    public void generateWeeklyReport();

    /** generates a monthly report */
    public void generateMonthlyReport();

    /** generates a yearly report */
    public void generateYearlyReport();
}
