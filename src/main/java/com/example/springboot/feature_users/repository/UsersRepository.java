package com.example.springboot.feature_users.repository;

import com.example.springboot.feature_users.entity.Users;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UsersRepository extends MongoRepository<Users, String> {
    Users findByUsername(String username);
}
