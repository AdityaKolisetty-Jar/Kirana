package com.example.springboot.feature_report_generation.controller;

import com.example.springboot.dto.ApiResponse;
import com.example.springboot.feature_report_generation.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.springboot.feature_report_generation.constants.ReportConstants.*;

@RestController
@RequestMapping("/report")
public class ReportController {

    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    /**
     * generates a report in the report table by weekly, monthly and yearly
     *
     * @param type
     * @return
     */
    //    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{type}")
    public String generateReport(@PathVariable String type) {
        reportService.requestReportGeneration(type.toUpperCase());
        return REPORT_GENERATION_REQUEST_FOR + type;
    }

    /**
     * retrieves all the reports
     *
     * @return
     */
    //    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllReports() {
        return ResponseEntity.ok(
                new ApiResponse(true, null, SUCCESS, null, reportService.getAllReports()));
    }
}
