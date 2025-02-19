package com.example.springboot.feature_record_transactions.repository;

import com.example.springboot.feature_record_transactions.entity.Transaction;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime now);
}
