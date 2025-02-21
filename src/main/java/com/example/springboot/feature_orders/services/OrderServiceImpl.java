package com.example.springboot.feature_orders.services;

import static com.example.springboot.feature_orders.constants.OrderConstants.*;

import com.example.springboot.dto.ApiResponse;
import com.example.springboot.feature_orders.daos.OrderDao;
import com.example.springboot.feature_orders.entities.Order;
import com.example.springboot.feature_orders.helpers.OrderHelper;
import com.example.springboot.feature_orders.models.OrderRequest;
import com.example.springboot.feature_orders.utils.OrderUtils;
import com.example.springboot.feature_record_transactions.daos.TransactionDao;
import com.example.springboot.feature_record_transactions.entities.Transaction;
import com.example.springboot.feature_record_transactions.services.CurrencyConversionService;
import java.text.MessageFormat;
import java.util.List;
import org.springframework.stereotype.Service;

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
                return new ApiResponse(false, USER_ID_REQUIRED, USER_ID_IS_REQUIRED, null, null);
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

            Transaction transaction =
                    OrderUtils.createTransaction(orderRequest, userId, totalAmount);
            transaction = transactionDao.save(transaction);

            Order savedOrder = orderDao.save(orderRequest, transaction.getTid(), totalAmount);

            ApiResponse apiResponse = new ApiResponse();
            apiResponse.setDisplayMsg(
                    MessageFormat.format(
                            "Order placed successfully and amount to be paid is :: {0} :: {1}",
                            convertedCurrency, orderRequest.getCurrency()));

            apiResponse.setData(savedOrder);
            return apiResponse;
        } catch (Exception e) {
            return new ApiResponse(
                    false, INTERNAL_ERROR, ERROR_WHILE_PROCESSING_ERROR, e.getMessage(), null);
        }
    }

    /**
     * returns all orders
     *
     * @return
     */
    @Override
    public ApiResponse getAllOrders() {
        List<Order> order = orderDao.findAll();
        return new ApiResponse(true, null, ORDER_RETRIEVED_SUCCESSFULLY, null, order);
    }
}
