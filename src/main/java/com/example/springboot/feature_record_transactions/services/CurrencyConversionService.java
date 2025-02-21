package com.example.springboot.feature_record_transactions.services;

public interface CurrencyConversionService {

    /**
     * converts amount to different currencies
     *
     * @param amount
     * @param srcCurrency
     * @param targetCurrency
     * @return
     */
    public double convertToCurrency(double amount, String srcCurrency, String targetCurrency);
}
