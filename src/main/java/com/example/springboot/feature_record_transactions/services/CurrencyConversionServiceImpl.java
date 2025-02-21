package com.example.springboot.feature_record_transactions.services;

import com.example.springboot.feature_record_transactions.daos.CurrencyConversionDao;
import org.springframework.stereotype.Service;

@Service
public class CurrencyConversionServiceImpl implements CurrencyConversionService {
    private final CurrencyConversionDao currencyConversionDao;

    public CurrencyConversionServiceImpl(CurrencyConversionDao currencyConversionDao) {
        this.currencyConversionDao = currencyConversionDao;
    }

    /**
     * converts amount to different currencies
     *
     * @param amount
     * @param srcCurrency
     * @param targetCurrency
     * @return
     */
    @Override
    public double convertToCurrency(double amount, String srcCurrency, String targetCurrency) {
        double exhangeRate = currencyConversionDao.getExchangeRate(srcCurrency, targetCurrency);
        return amount * exhangeRate;
    }
}
