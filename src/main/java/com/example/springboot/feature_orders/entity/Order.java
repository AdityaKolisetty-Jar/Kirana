package com.example.springboot.feature_orders.entity;

import org.springframework.data.annotation.Id;
import java.util.List;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "orders")
public class Order {
    @Id private String orderId; // Unique Order ID
    private Long transactionId;
    private List<Long> productIds;
    private String currency;
    private double totalAmount;
}
