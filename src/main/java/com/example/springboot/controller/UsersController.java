package com.example.springboot.controller;

import com.example.springboot.daos.UsersDao;
import com.example.springboot.dto.ApiResponse;
import com.example.springboot.entity.Users;
import com.example.springboot.service.UsersService;
import com.example.springboot.util.JwtUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import static com.example.springboot.constants.UserLogConstants.*;
import static com.example.springboot.constants.UsersConstants.*;

@Slf4j
@RestController
@RequestMapping("/users") // Base path for user-related APIs
public class UsersController {

    private final UsersService usersService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UsersDao usersDao;

    @Autowired
    public UsersController(
            UsersService usersService,
            AuthenticationManager authenticationManager,
            JwtUtil jwtUtil,
            UsersDao usersDao) {
        this.usersService = usersService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.usersDao = usersDao;
    }

    /**
     * login as a user
     *
     * @param user
     * @return
     */
    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody Users user) {
        try {
            String username = user.getUsername();
            List<String> roles = usersService.getUserRolesByUsername(username);
            String userId = usersService.getUserIdByUsername(username);

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, user.getPassword()));

            String jwtToken = jwtUtil.generateToken(username, roles, userId);

            Map<String, String> tokenResponse = new HashMap<>();
            tokenResponse.put(JWT_TOKEN, jwtToken);

            return ResponseEntity.ok(
                    new ApiResponse(true, null, LOGIN_SUCCESSFUL, null, tokenResponse));

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(
                            new ApiResponse(
                                    false,
                                    INVALID_CREDENTIALS,
                                    INVALID_USERNAME_OR_PASSWORD,
                                    null,
                                    null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(
                            new ApiResponse(
                                    false,
                                    INTERNAL_ERROR,
                                    AN_UNEXPECTED_ERROR_OCCURRED,
                                    e.getMessage(),
                                    null));
        }
    }

    /**
     * register a user
     *
     * @param user
     * @return
     */
    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@RequestBody Users user) {
        log.info(CALLED);
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        return ResponseEntity.ok(
                new ApiResponse(
                        true, null, REGISTRATION_SUCCESSFUL, null, usersService.save(user)));
    }
}
