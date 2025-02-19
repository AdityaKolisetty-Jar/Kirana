package com.example.springboot.feature_report_generation.service;

import com.example.springboot.feature_report_generation.entity.Report;
import java.util.List;

public interface KafkaProducerService {
    List<Report> getAllReports();

    void requestReportGeneration(String reportType);
}
