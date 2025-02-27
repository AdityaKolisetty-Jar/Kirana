package com.example.springboot.feature_report_generation.services;

import static com.example.springboot.feature_report_generation.constants.ReportConstants.*;
import static com.example.springboot.feature_report_generation.constants.ReportConstants.INVALID_REPORT_TYPE;

import com.example.springboot.feature_record_transactions.daos.TransactionDao;
import com.example.springboot.feature_record_transactions.entities.Transaction;
import com.example.springboot.feature_report_generation.daos.ReportDao;
import com.example.springboot.feature_report_generation.entities.Report;
import com.example.springboot.feature_report_generation.utils.ReportUtils;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerServiceImpl implements KafkaConsumerService {

    private final TransactionDao transactionDao;
    private final ReportDao reportDao;

    public KafkaConsumerServiceImpl(TransactionDao transactionDao, ReportDao reportDao) {
        this.transactionDao = transactionDao;
        this.reportDao = reportDao;
    }

    /**
     * processes report requests and listens to messages passed in kafka
     *
     * @param reportType
     */
    @KafkaListener(topics = REPORT_TOPIC, groupId = REPORT_GROUP)
    public void processReportRequest(String reportType) {
        switch (reportType.toUpperCase()) {
            case WEEK_UP:
                generateWeeklyReport();
                break;
            case MONTH_UP:
                generateMonthlyReport();
                break;
            case YEAR_UP:
                generateYearlyReport();
                break;
            default:
                throw new IllegalArgumentException(INVALID_REPORT_TYPE);
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
        saveReport(transactions, WEEK_UP);
    }

    /** generates monthly reports */
    public void generateMonthlyReport() {
        LocalDateTime startOfMonth =
                LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()).atStartOfDay();
        LocalDateTime endOfMonth =
                LocalDate.now().with(TemporalAdjusters.lastDayOfMonth()).atTime(23, 59, 59);

        List<Transaction> transactions =
                transactionDao.findByCreatedAtBetween(startOfMonth, endOfMonth);
        saveReport(transactions, MONTH_UP);
    }

    /** generates yearly reports */
    public void generateYearlyReport() {
        LocalDateTime startOfYear =
                LocalDate.now().with(TemporalAdjusters.firstDayOfYear()).atStartOfDay();
        LocalDateTime endOfYear =
                LocalDate.now().with(TemporalAdjusters.lastDayOfYear()).atTime(23, 59, 59);

        List<Transaction> transactions =
                transactionDao.findByCreatedAtBetween(startOfYear, endOfYear);
        saveReport(transactions, YEAR_UP);
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

        Report report = ReportUtils.createReport(transactionIds, totalAmount, reportType);

        reportDao.save(report);
    }
}
