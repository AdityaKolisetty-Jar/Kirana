package com.example.springboot.filter;

import com.example.springboot.service.CustomUserDetailsService;
import com.example.springboot.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import static com.example.springboot.constants.JwtFilterConstants.*;
import static com.example.springboot.constants.JwtFilterLogConstants.*;

@Component
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    @Autowired private JwtUtil jwtUtil;

    @Autowired private CustomUserDetailsService userDetailsService;

    /**
     * intercepts http requests and validates the jwt token
     *
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        log.info(
                JWT_FILTER_REQUEST + request.getMethod() + " " + request.getRequestURI());

        String path = request.getServletPath();
        if (path.equals("/users/login")
                || path.equals("/users/register")
                || path.startsWith("/actuator")) {
            log.info(SKIPPING_JWT_FILTER_FOR + path);
            filterChain.doFilter(request, response);
            return;
        }

        String authorizationHeader = request.getHeader(AUTHORIZATION);
        log.info(AUTHORIZATION_HEADER + authorizationHeader);

        if (authorizationHeader == null || !authorizationHeader.startsWith(BEARER)) {
            log.info(NO_JWT_TOKEN_FOUND);
            response.sendError(
                    HttpServletResponse.SC_UNAUTHORIZED, UNAUTHORIZATION_JWT_NOT_FOUND);
            return;
        }

        String token = authorizationHeader.substring(7);
        String username = jwtUtil.extractUsername(token);
        List<String> roles = jwtUtil.extractRoles(token);

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (jwtUtil.validateToken(token, userDetails.getUsername())) {
                List<SimpleGrantedAuthority> authorities =
                        roles.stream()
                                .map(role -> new SimpleGrantedAuthority(ROLE + role))
                                .collect(Collectors.toList());

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null, authorities);

                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.info(USER_AUTHENTICATED + username);
                log.info(USER_ROLES + roles);
            } else {
                log.info(INVALID_JWT_TOKEN);
                response.sendError(
                        HttpServletResponse.SC_UNAUTHORIZED, UNAUTHORIZED_JWT_INVALID);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
