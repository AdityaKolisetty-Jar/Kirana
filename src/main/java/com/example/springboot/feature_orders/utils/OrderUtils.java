package com.example.springboot.feature_orders.utils;

import static com.example.springboot.feature_orders.constants.OrderConstants.ORDER;

import com.example.springboot.feature_orders.models.OrderRequest;
import com.example.springboot.feature_record_transactions.entities.Transaction;
import org.springframework.stereotype.Component;

@Component
public class OrderUtils {

    /**
     * creates a transaction with orderRequest, userId and totalAmount
     *
     * @param orderRequest
     * @param userId
     * @param totalAmount
     * @return
     */
    public static Transaction createTransaction(
            OrderRequest orderRequest, Long userId, double totalAmount) {
        Transaction transaction = new Transaction();
        transaction.setUid(userId);
        transaction.setAmount(totalAmount);
        transaction.setCurrency(orderRequest.getCurrency());
        transaction.setTransactionType(ORDER);
        return transaction;
    }
}
