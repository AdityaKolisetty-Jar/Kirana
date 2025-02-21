package com.example.springboot.feature_users.services;

import com.example.springboot.feature_users.entities.Users;
import java.util.List;

public interface UsersService {

    /**
     * saves a user
     *
     * @param user
     * @return
     */
    public Users save(Users user);

    /**
     * returns UserId by username
     *
     * @param username
     * @return
     */
    public String getUserIdByUsername(String username);

    /**
     * returns user roles by username
     *
     * @param username
     * @return
     */
    public List<String> getUserRolesByUsername(String username);
}
