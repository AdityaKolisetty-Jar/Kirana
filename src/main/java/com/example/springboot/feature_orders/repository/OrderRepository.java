package com.example.springboot.feature_orders.repository;

import com.example.springboot.feature_orders.entity.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends MongoRepository<Order, String> {}
