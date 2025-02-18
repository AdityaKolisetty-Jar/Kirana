// package com.example.springboot.service;
//
// import io.github.bucket4j.*;
// import org.springframework.stereotype.Service;
//
// import java.time.Duration;
// import java.util.concurrent.ConcurrentHashMap;
// import java.util.concurrent.ConcurrentMap;
//
// @Service
// public class RateLimiterService {
//    private final ConcurrentMap<String, Bucket> cache = new ConcurrentHashMap<>();
//
//    private Bucket createNewBucket() {
//        return Bucket4j.builder()
//                .addLimit(Bandwidth.classic(10, Refill.intervally(10, Duration.ofMinutes(1)))) //
// 10 requests per minute
//                .build();
//    }
//
//    public boolean allowRequest(String identifier) {
//        Bucket bucket = cache.computeIfAbsent(identifier, k -> createNewBucket());
//        return bucket.tryConsume(1);
//    }
// }
