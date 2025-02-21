package com.example.springboot.feature_report_generation.utils;

import com.example.springboot.feature_report_generation.entities.Report;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ReportUtils {

    /**
     * creates a report entry in mongodb using transactionIds, totalAmount and reportType
     *
     * @param transactionIds
     * @param totalAmount
     * @param reportType
     * @return
     */
    public static Report createReport(
            List<Long> transactionIds, double totalAmount, String reportType) {
        Report report = new Report();
        report.setTransactionIds(transactionIds);
        report.setTotalAmount(totalAmount);
        report.setReportType(reportType);
        report.setCreatedAt(LocalDateTime.now());
        return report;
    }
}
