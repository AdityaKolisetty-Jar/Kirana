package com.example.springboot.feature_report_generation.helper;

import com.example.springboot.feature_report_generation.entity.Report;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class CreateReportHelper {

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
