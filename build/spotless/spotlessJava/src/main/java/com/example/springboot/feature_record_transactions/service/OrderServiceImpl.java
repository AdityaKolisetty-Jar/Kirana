package com.example.springboot.feature_record_transactions.service;

import com.example.springboot.dto.ApiResponse;
import com.example.springboot.entity.Transaction;
import com.example.springboot.feature_record_transactions.daos.OrderDao;
import com.example.springboot.feature_record_transactions.dtos.OrderRequest;
import com.example.springboot.feature_record_transactions.entity.Order;
import com.example.springboot.feature_record_transactions.helper.OrderHelper;
import com.example.springboot.repository.TransactionRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderDao orderDao;
    private final TransactionRepository transactionRepository;
    private final CurrencyConversionService currencyConversionService;
    private final OrderHelper orderHelper;

    public OrderServiceImpl(
            OrderDao orderDao,
            TransactionRepository transactionRepository,
            CurrencyConversionService currencyConversionService,
            OrderHelper orderHelper) {
        this.orderDao = orderDao;
        this.transactionRepository = transactionRepository;
        this.currencyConversionService = currencyConversionService;
        this.orderHelper = orderHelper;
    }

    public ApiResponse createOrder(Long userId, OrderRequest orderRequest) {
        try {
            if (userId == null) {
                return new ApiResponse(
                        false, "USER_ID_REQUIRED", "User ID is required", null, null);
            }

            ApiResponse productResponse =
                    orderHelper.validateAndProcessProducts(
                            orderRequest.getProductIds(), orderRequest.getCurrency());
            if (!productResponse.isSuccess()) {
                return productResponse;
            }

            double totalAmount = (double) productResponse.getData();
            double convertedCurrency =
                    currencyConversionService.convertToCurrency(
                            totalAmount, "INR", orderRequest.getCurrency());

            Transaction transaction = new Transaction();
            transaction.setUid(userId);
            transaction.setAmount(totalAmount);
            transaction.setCurrency(orderRequest.getCurrency());
            transaction.setTransactionType("ORDER");
            transaction = transactionRepository.save(transaction);

            Order savedOrder = orderDao.save(orderRequest, transaction.getTid(), totalAmount);

            return new ApiResponse(
                    true,
                    "Order placed successfully and the amount to be paid is "
                            + convertedCurrency
                            + " "
                            + orderRequest.getCurrency(),
                    savedOrder);
        } catch (Exception e) {
            return new ApiResponse(
                    false,
                    "INTERNAL_ERROR",
                    "An error occurred while processing the order",
                    e.getMessage(),
                    null);
        }
    }
}
