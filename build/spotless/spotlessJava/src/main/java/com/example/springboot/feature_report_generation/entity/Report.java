package com.example.springboot.feature_report_generation.entity;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "reports")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Report {
    @Id private String id;
    private List<Long> transactionIds;
    private Double totalAmount;
    private String reportType; // WEEKLY, MONTHLY, YEARLY
    private LocalDateTime createdAt;

    public Report(
            Object o,
            String reportType,
            double totalCredits,
            double totalDebits,
            double netFlow,
            LocalDateTime now) {}
}
