package com.example.springboot.service;

import com.example.springboot.dto.ApiResponse;
import com.example.springboot.entity.Transaction;

public interface TransactionService {
    ApiResponse createTransaction(Transaction transaction);

    ApiResponse getAllTransactions();
}
