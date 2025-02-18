package com.example.springboot.daos;

import com.example.springboot.entity.Users;
import com.example.springboot.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UsersDao {

    private final UsersRepository usersRepository;

    @Autowired
    public UsersDao(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    /**
     * saves a user
     *
     * @param user
     * @return
     */
    public Users save(Users user) {
        return usersRepository.save(user);
    }

    /**
     * finds user by username
     *
     * @param username
     * @return
     */
    public Users findByUsername(String username) {
        return usersRepository.findByUsername(username);
    }
}
