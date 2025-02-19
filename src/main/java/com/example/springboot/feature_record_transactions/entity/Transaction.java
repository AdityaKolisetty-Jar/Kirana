package com.example.springboot.feature_record_transactions.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tid;

    private Long uid;
    private Double amount;
    private String currency;
    private String transactionType;
    @CreationTimestamp private LocalDateTime createdAt;
}
