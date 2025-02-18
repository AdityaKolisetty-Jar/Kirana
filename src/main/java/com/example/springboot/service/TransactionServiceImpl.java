package com.example.springboot.service;

import com.example.springboot.daos.TransactionDao;
import com.example.springboot.dto.ApiResponse;
import com.example.springboot.entity.Transaction;
import java.util.List;
import org.springframework.stereotype.Service;

import static com.example.springboot.constants.TransactionConstants.*;

@Service
public class TransactionServiceImpl implements TransactionService {
    private final TransactionDao transactionDao;

    public TransactionServiceImpl(TransactionDao transactionDao) {
        this.transactionDao = transactionDao;
    }

    /**
     * creates a transaction
     *
     * @param transaction
     * @return
     */
    @Override
    public ApiResponse createTransaction(Transaction transaction) {
        Transaction savedTransaction = transactionDao.save(transaction);
        return new ApiResponse(
                true, null, TRANSACTION_CREATED_SUCCESSFULLY, null, savedTransaction);
    }

    /**
     * returns all transactions
     *
     * @return
     */
    @Override
    public ApiResponse getAllTransactions() {
        List<Transaction> transactions = transactionDao.findAll();
        return new ApiResponse(
                true, null, TRANSACTION_RETRIEVED_SUCCESSFULLY, null, transactions);
    }
}
