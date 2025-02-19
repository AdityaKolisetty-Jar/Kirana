package com.example.springboot.feature_users.service;

import com.example.springboot.feature_users.entity.Users;
import java.util.List;

public interface UsersService {
    public Users save(Users user);

    public String getUserIdByUsername(String username);

    public List<String> getUserRolesByUsername(String username);
}
