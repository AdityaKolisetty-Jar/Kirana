package com.example.springboot.feature_record_transactions.daos;

import com.example.springboot.feature_record_transactions.dtos.OrderRequest;
import com.example.springboot.feature_record_transactions.entity.Order;
import com.example.springboot.feature_record_transactions.repository.OrderRepository;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public class OrderDao {
    private final OrderRepository orderRepository;

    public OrderDao(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    /**
     * saves an order
     *
     * @param orderRequest
     * @param transactionId
     * @param totalAmount
     * @return
     */
    public Order save(OrderRequest orderRequest, Long transactionId, double totalAmount) {
        Order order = new Order();
        order.setOrderId(UUID.randomUUID().toString());
        order.setTransactionId(transactionId);
        order.setProductIds(orderRequest.getProductIds());
        order.setCurrency(orderRequest.getCurrency());
        order.setTotalAmount(totalAmount);

        return orderRepository.save(order);
    }
}
