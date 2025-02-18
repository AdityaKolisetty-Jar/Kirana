package com.example.springboot.feature_record_transactions.repository;

import com.example.springboot.feature_record_transactions.entity.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends MongoRepository<Order, String> {}
