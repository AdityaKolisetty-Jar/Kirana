package com.example.springboot.feature_record_transactions.services;

import com.example.springboot.dto.ApiResponse;
import com.example.springboot.feature_record_transactions.entities.Transaction;

public interface TransactionService {

    /**
     * creates a transaction
     *
     * @param transaction
     * @return
     */
    ApiResponse createTransaction(Transaction transaction);

    /**
     * returns all transactions
     *
     * @return
     */
    ApiResponse getAllTransactions();
}
