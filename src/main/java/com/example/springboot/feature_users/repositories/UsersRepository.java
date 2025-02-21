package com.example.springboot.feature_users.repositories;

import com.example.springboot.feature_users.entities.Users;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UsersRepository extends MongoRepository<Users, String> {

    /**
     * finds users by username
     *
     * @param username
     * @return
     */
    Users findByUsername(String username);
}
