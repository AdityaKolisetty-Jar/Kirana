package com.example.springboot.feature_record_transactions.daos;

import com.example.springboot.feature_record_transactions.entities.Transaction;
import com.example.springboot.feature_record_transactions.repositories.TransactionRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TransactionDao {
    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionDao(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    /**
     * saves a transaction
     *
     * @param transaction
     * @return
     */
    public Transaction save(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    /**
     * finds all transactions
     *
     * @return
     */
    public List<Transaction> findAll() {
        return transactionRepository.findAll();
    }

    /**
     * returns list of transactions between the given dates and times
     *
     * @param startOfWeek
     * @param endOfWeek
     * @return
     */
    public List<Transaction> findByCreatedAtBetween(
            LocalDateTime startOfWeek, LocalDateTime endOfWeek) {
        return transactionRepository.findByCreatedAtBetween(startOfWeek, endOfWeek);
    }
}
