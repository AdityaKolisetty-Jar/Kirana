package com.example.springboot.feature_report_generation.service;

import com.example.springboot.feature_report_generation.entity.Report;
import java.util.List;

public interface ReportService {
    List<Report> getAllReports();

    void requestReportGeneration(String reportType);

    void processReportRequest(String reportType);

    void generateWeeklyReport();

    void generateMonthlyReport();

    void generateYearlyReport();
}
