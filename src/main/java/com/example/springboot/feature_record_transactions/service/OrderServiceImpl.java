package com.example.springboot.feature_record_transactions.service;

import com.example.springboot.daos.TransactionDao;
import com.example.springboot.dto.ApiResponse;
import com.example.springboot.entity.Transaction;
import com.example.springboot.feature_record_transactions.daos.OrderDao;
import com.example.springboot.feature_record_transactions.dtos.OrderRequest;
import com.example.springboot.feature_record_transactions.entity.Order;
import com.example.springboot.feature_record_transactions.helper.OrderHelper;
import com.example.springboot.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import static com.example.springboot.feature_record_transactions.constants.OrderConstants.*;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderDao orderDao;
    private final TransactionDao transactionDao;
    private final CurrencyConversionService currencyConversionService;
    private final OrderHelper orderHelper;

    public OrderServiceImpl(
            OrderDao orderDao,
            TransactionDao transactionDao,
            CurrencyConversionService currencyConversionService,
            OrderHelper orderHelper) {
        this.orderDao = orderDao;
        this.transactionDao = transactionDao;
        this.currencyConversionService = currencyConversionService;
        this.orderHelper = orderHelper;
    }

    /**
     * creates an order
     *
     * @param userId
     * @param orderRequest
     * @return
     */
    public ApiResponse createOrder(Long userId, OrderRequest orderRequest) {
        try {
            if (userId == null) {
                return new ApiResponse(
                        false, USER_ID_REQUIRED, USER_ID_IS_REQUIRED, null, null);
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
                            totalAmount, INR, orderRequest.getCurrency());

            Transaction transaction = new Transaction();
            transaction.setUid(userId);
            transaction.setAmount(totalAmount);
            transaction.setCurrency(orderRequest.getCurrency());
            transaction.setTransactionType(ORDER);
            transaction = transactionDao.save(transaction);

            Order savedOrder = orderDao.save(orderRequest, transaction.getTid(), totalAmount);

            return new ApiResponse(
                    true,
                    ORDER_PLACED_SUCCESSFULLY_AND_AMOUNT_PAY
                            + convertedCurrency
                            + " "
                            + orderRequest.getCurrency(),
                    savedOrder);
        } catch (Exception e) {
            return new ApiResponse(
                    false,
                    INTERNAL_ERROR,
                    ERROR_WHILE_PROCESSING_ERROR,
                    e.getMessage(),
                    null);
        }
    }
}
