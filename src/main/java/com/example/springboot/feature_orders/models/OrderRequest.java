package com.example.springboot.feature_orders.models;

import java.util.List;
import lombok.Data;

@Data
public class OrderRequest {
    private Long userId; // Add userId
    private List<Long> productIds;
    private String currency;
}
