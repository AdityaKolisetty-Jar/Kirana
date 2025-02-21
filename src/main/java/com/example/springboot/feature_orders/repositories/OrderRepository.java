package com.example.springboot.feature_orders.repositories;

import com.example.springboot.feature_orders.entities.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends MongoRepository<Order, String> {}
