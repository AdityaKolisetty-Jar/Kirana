package com.example.springboot.feature_report_generation.services;

import com.example.springboot.feature_report_generation.entities.Report;
import java.util.List;

public interface KafkaProducerService {
    /**
     * returns all reports
     *
     * @return
     */
    List<Report> getAllReports();

    /**
     * sends a message through kafka requesting for generation of reports
     *
     * @param reportType
     */
    void requestReportGeneration(String reportType);
}
