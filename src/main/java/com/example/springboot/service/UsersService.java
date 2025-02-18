package com.example.springboot.service;

import com.example.springboot.entity.Users;
import java.util.List;
import java.util.Optional;

public interface UsersService {
    public Users save(Users user);

    public String getUserIdByUsername(String username);

    public List<String> getUserRolesByUsername(String username);
}
