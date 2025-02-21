package com.example.springboot.feature_users.services;

import static com.example.springboot.feature_users.constants.UserLogConstants.*;
import static com.example.springboot.feature_users.constants.UsersConstants.*;

import com.example.springboot.feature_users.daos.UsersDao;
import com.example.springboot.feature_users.entities.Users;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    private final UsersDao usersDao;

    @Autowired
    public CustomUserDetailsService(UsersDao usersDao) {
        this.usersDao = usersDao;
    }

    /**
     * loads user by username
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info(SEARCHING_FOR_USERNAME + username);

        Optional<Users> userOptional = Optional.ofNullable(usersDao.findByUsername(username));

        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException(USER_NOT_FOUND);
        }

        Users user = userOptional.get();
        List<String> roleNames = user.getRoles();

        log.info(USER_FOUND + user);
        return User.withUsername(user.getUsername())
                .password(user.getPassword())
                .roles(roleNames.toArray(new String[0]))
                .build();
    }
}
