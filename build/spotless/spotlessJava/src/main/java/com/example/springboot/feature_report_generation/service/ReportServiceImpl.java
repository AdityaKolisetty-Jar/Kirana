package com.example.springboot.feature_report_generation.service;

import com.example.springboot.daos.TransactionDao;
import com.example.springboot.entity.Transaction;
import com.example.springboot.feature_report_generation.dao.ReportDao;
import com.example.springboot.feature_report_generation.entity.Report;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ReportServiceImpl implements ReportService {
    private final TransactionDao transactionDao;
    private final ReportDao reportDao;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public ReportServiceImpl(
            TransactionDao transactionDao,
            ReportDao reportDao,
            KafkaTemplate<String, String> kafkaTemplate) {
        this.transactionDao = transactionDao;
        this.reportDao = reportDao;
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * returns all reports
     *
     * @return
     */
    public List<Report> getAllReports() {
        return reportDao.findAll();
    }

    /**
     * requests generation of reports through kafka
     *
     * @param reportType
     */
    public void requestReportGeneration(String reportType) {
        kafkaTemplate.send("report-topic", reportType);
    }

    @KafkaListener(topics = "report-topic", groupId = "report-group")
    public void processReportRequest(String reportType) {
        switch (reportType.toUpperCase()) {
            case "WEEKLY":
                generateWeeklyReport();
                break;
            case "MONTHLY":
                generateMonthlyReport();
                break;
            case "YEARLY":
                generateYearlyReport();
                break;
            default:
                throw new IllegalArgumentException("Invalid report type");
        }
    }

    /** generates weekly reports */
    public void generateWeeklyReport() {
        LocalDateTime startOfWeek =
                LocalDate.now()
                        .with(TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY))
                        .atStartOfDay();
        LocalDateTime endOfWeek =
                startOfWeek.plusDays(6).plusHours(23).plusMinutes(59).plusSeconds(59);

        List<Transaction> transactions =
                transactionDao.findByCreatedAtBetween(startOfWeek, endOfWeek);
        saveReport(transactions, "WEEKLY");
    }

    /** generates monthly reports */
    public void generateMonthlyReport() {
        LocalDateTime startOfMonth =
                LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()).atStartOfDay();
        LocalDateTime endOfMonth =
                LocalDate.now().with(TemporalAdjusters.lastDayOfMonth()).atTime(23, 59, 59);

        List<Transaction> transactions =
                transactionDao.findByCreatedAtBetween(startOfMonth, endOfMonth);
        saveReport(transactions, "MONTHLY");
    }

    /** generates yearly reports */
    public void generateYearlyReport() {
        LocalDateTime startOfYear =
                LocalDate.now().with(TemporalAdjusters.firstDayOfYear()).atStartOfDay();
        LocalDateTime endOfYear =
                LocalDate.now().with(TemporalAdjusters.lastDayOfYear()).atTime(23, 59, 59);

        List<Transaction> transactions =
                transactionDao.findByCreatedAtBetween(startOfYear, endOfYear);
        saveReport(transactions, "YEARLY");
    }

    /**
     * saves a report in mongodb database
     *
     * @param transactions
     * @param reportType
     */
    private void saveReport(List<Transaction> transactions, String reportType) {
        List<Long> transactionIds =
                transactions.stream().map(Transaction::getTid).collect(Collectors.toList());
        double totalAmount = transactions.stream().mapToDouble(Transaction::getAmount).sum();

        Report report = new Report();
        report.setTransactionIds(transactionIds);
        report.setTotalAmount(totalAmount);
        report.setReportType(reportType);
        report.setCreatedAt(LocalDateTime.now());

        reportDao.save(report);
    }
}
