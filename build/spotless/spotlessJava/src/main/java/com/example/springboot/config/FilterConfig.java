// package com.example.springboot.config;
//
// import com.example.springboot.filter.RateLimitingFilter;
// import jakarta.servlet.Filter;
// import jakarta.servlet.FilterRegistrationBean;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
//
// @Configuration
// public class FilterConfig {
//    private final RateLimitingFilter rateLimitingFilter;
//
//    public FilterConfig(RateLimitingFilter rateLimitingFilter) {
//        this.rateLimitingFilter = rateLimitingFilter;
//    }
//
//    @Bean
//    public FilterRegistrationBean<Filter> rateLimitFilter() {
//        FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<>();
//        registrationBean.setFilter(rateLimitingFilter);
//        registrationBean.addUrlPatterns("/*"); // Apply globally
//        registrationBean.setOrder(1);
//        return registrationBean;
//    }
// }
