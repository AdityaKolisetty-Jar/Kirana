package com.example.springboot.feature_record_transactions.controller;

import com.example.springboot.dto.ApiResponse;
import com.example.springboot.feature_record_transactions.entity.Transaction;
import com.example.springboot.feature_record_transactions.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    /**
     * create transactions
     *
     * @param transaction
     * @return
     */
    @PostMapping
    public ResponseEntity<ApiResponse> createTransaction(@RequestBody Transaction transaction) {
        ApiResponse response = transactionService.createTransaction(transaction);
        return ResponseEntity.ok(response);
    }

    /**
     * returns all transactions
     *
     * @returnB
     */
//    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<ApiResponse> getAllTransactions() {
        ApiResponse response = transactionService.getAllTransactions();
        return ResponseEntity.ok(response);
    }
}
