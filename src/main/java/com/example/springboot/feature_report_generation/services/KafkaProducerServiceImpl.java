package com.example.springboot.feature_report_generation.services;

import static com.example.springboot.feature_report_generation.constants.ReportConstants.*;

import com.example.springboot.feature_record_transactions.daos.TransactionDao;
import com.example.springboot.feature_report_generation.daos.ReportDao;
import com.example.springboot.feature_report_generation.entities.Report;
import java.util.List;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerServiceImpl implements KafkaProducerService {
    private final TransactionDao transactionDao;
    private final ReportDao reportDao;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaProducerServiceImpl(
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
     * sends a message through kafka requesting for generation of reports
     *
     * @param reportType
     */
    public void requestReportGeneration(String reportType) {
        kafkaTemplate.send(REPORT_TOPIC, reportType);
    }
}
