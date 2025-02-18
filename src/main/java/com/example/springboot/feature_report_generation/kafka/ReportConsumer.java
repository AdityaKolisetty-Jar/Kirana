package com.example.springboot.feature_report_generation.kafka;

import com.example.springboot.entity.Transaction;
import com.example.springboot.feature_report_generation.entity.Report;
import com.example.springboot.feature_report_generation.repository.ReportRepository;
import com.example.springboot.repository.TransactionRepository;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import static com.example.springboot.feature_report_generation.constants.ReportConstants.*;

@Service
public class ReportConsumer {

    private final TransactionRepository transactionRepository;
    private final ReportRepository reportRepository;

    public ReportConsumer(
            TransactionRepository transactionRepository, ReportRepository reportRepository) {
        this.transactionRepository = transactionRepository;
        this.reportRepository = reportRepository;
    }

    /**
     * listens to messages and generates a weekly/monthly/yearly reports
     *
     * @param reportRequest
     */
    @KafkaListener(topics = REPORT_TOPIC, groupId = REPORT_GROUP)
    public void generateReport(Report reportRequest) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startDate =
                switch (reportRequest.getReportType().toLowerCase()) {
                    case WEEK_LOW -> now.minus(7, ChronoUnit.DAYS);
                    case MONTH_LOW -> now.minus(1, ChronoUnit.MONTHS);
                    case YEAR_LOW -> now.minus(1, ChronoUnit.YEARS);
                    default -> throw new IllegalArgumentException("Invalid report type");
                };

        List<Transaction> transactions =
                transactionRepository.findByCreatedAtBetween(startDate, now);
        double totalCredits =
                transactions.stream()
                        .filter(t -> t.getTransactionType().equalsIgnoreCase(CREDIT))
                        .mapToDouble(Transaction::getAmount)
                        .sum();
        double totalDebits =
                transactions.stream()
                        .filter(t -> t.getTransactionType().equalsIgnoreCase(DEBIT))
                        .mapToDouble(Transaction::getAmount)
                        .sum();
        double netFlow = totalCredits - totalDebits;

        Report report =
                new Report(
                        null,
                        reportRequest.getReportType(),
                        totalCredits,
                        totalDebits,
                        netFlow,
                        now);
        reportRepository.save(report);
    }
}
