package com.example.springboot.feature_report_generation.daos;

import com.example.springboot.feature_report_generation.entities.Report;
import com.example.springboot.feature_report_generation.repositories.ReportRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReportDao {
    private final ReportRepository reportRepository;

    @Autowired
    public ReportDao(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    /**
     * Saves a report in the MongoDB database.
     *
     * @param report The report entity to be saved.
     * @return The saved report.
     */
    public Report save(Report report) {
        return reportRepository.save(report);
    }

    /**
     * Finds a report by its ID.
     *
     * @param reportId The ID of the report.
     * @return An Optional containing the report if found.
     */
    public Optional<Report> getReportById(String reportId) {
        return reportRepository.findById(reportId);
    }

    /**
     * Deletes a report by its ID.
     *
     * @param reportId The ID of the report.
     */
    public void deleteReportById(String reportId) {
        reportRepository.deleteById(reportId);
    }

    /**
     * returns all reports
     *
     * @return
     */
    public List<Report> findAll() {
        return reportRepository.findAll();
    }
}
