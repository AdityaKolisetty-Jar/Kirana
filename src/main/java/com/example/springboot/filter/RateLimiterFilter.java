package com.example.springboot.filter;

import static com.example.springboot.constants.RateLimiterFilterConstants.*;
import static com.example.springboot.constants.RateLimiterFilterLogConstants.*;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@Slf4j
public class RateLimiterFilter extends OncePerRequestFilter {

    private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();

    /**
     * Creates a Bucket with a limit of 10 requests per minute
     *
     * @return Bucket
     */
    private Bucket createNewBucket() {
        return Bucket.builder()
                .addLimit(Bandwidth.classic(10, Refill.greedy(10, Duration.ofMinutes(1))))
                .build();
    }

    /**
     * Adds a filter for rate limiting, excluding Actuator Prometheus endpoint
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

        String requestURI = request.getRequestURI();

        if (requestURI.startsWith("/actuator/prometheus")) {
            filterChain.doFilter(request, response);
            return;
        }

        String clientIp = request.getRemoteAddr();
        Bucket bucket = buckets.computeIfAbsent(clientIp, k -> createNewBucket());

        log.info(CLIENT_IP + "{}" + AVAILABLE_TOKENS + "{}", clientIp, bucket.getAvailableTokens());

        if (!bucket.tryConsume(1)) {
            response.setStatus(429);
            response.getWriter().write(TOO_MANY_REQUESTS);
            return;
        }

        filterChain.doFilter(request, response);
    }
}
