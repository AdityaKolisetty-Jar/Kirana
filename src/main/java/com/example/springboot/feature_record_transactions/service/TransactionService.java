package com.example.springboot.feature_record_transactions.service;

import com.example.springboot.dto.ApiResponse;
import com.example.springboot.feature_record_transactions.entity.Transaction;

public interface TransactionService {
    ApiResponse createTransaction(Transaction transaction);

    ApiResponse getAllTransactions();
}
