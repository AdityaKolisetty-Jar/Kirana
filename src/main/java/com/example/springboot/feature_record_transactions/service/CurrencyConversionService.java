package com.example.springboot.feature_record_transactions.service;

public interface CurrencyConversionService {
    public double convertToCurrency(double amount, String srcCurrency, String targetCurrency);
}
