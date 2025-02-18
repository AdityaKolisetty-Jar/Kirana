package com.example.springboot.service;

import com.example.springboot.daos.UsersDao;
import com.example.springboot.entity.Users;
import com.example.springboot.repository.UsersRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsersDao usersDao;

    @Autowired
    public CustomUserDetailsService(UsersDao usersDao) { this.usersDao = usersDao; }

    /**
     * loads user by username
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Searching for username: " + username);

        Optional<Users> userOptional =
                Optional.ofNullable(usersDao.findByUsername(username));

        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }

        Users user = userOptional.get();
        List<String> roleNames = user.getRoles();

        System.out.println("User found: " + user);
        return User.withUsername(user.getUsername())
                .password(user.getPassword())
                .roles(roleNames.toArray(new String[0]))
                .build();
    }
}
