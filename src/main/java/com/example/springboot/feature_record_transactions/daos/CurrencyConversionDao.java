package com.example.springboot.feature_record_transactions.daos;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import static com.example.springboot.feature_record_transactions.constants.CurrencyConversionConstants.*;

@Repository
public class CurrencyConversionDao {
    private static final String API_URL = "https://api.fxratesapi.com/latest";
    private final StringRedisTemplate redisTemplate;

    public CurrencyConversionDao(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * fetches the exchange rate from the api and also helps in caaching with the help of redis
     *
     * @param srcCurrency
     * @param targetCurrency
     * @return
     */
    public double getExchangeRate(String srcCurrency, String targetCurrency) {

        String cacheKey = srcCurrency + "-" + targetCurrency;

        String cachedRate = redisTemplate.opsForValue().get(cacheKey);
        if (cachedRate != null) {
            return Double.parseDouble(cachedRate); // Return cached value
        }

        try {
            String url =
                    UriComponentsBuilder.fromHttpUrl(API_URL)
                            .queryParam(BASE, srcCurrency)
                            .toUriString();

            RestTemplate restTemplate = new RestTemplate();
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);

            if (response != null && response.containsKey(RATES)) {
                Map<String, Double> rates = (Map<String, Double>) response.get(RATES);
                double exchangeRate = rates.getOrDefault(targetCurrency, 0.0);

                if (exchangeRate > 0) {
                    redisTemplate
                            .opsForValue()
                            .set(cacheKey, String.valueOf(exchangeRate), 1, TimeUnit.HOURS);
                }

                return exchangeRate;
            }
        } catch (Exception e) {
            System.err.println(ERROR_FETCHING_EXCHANGE_RATES + e.getMessage());
        }

        return 0.0;
    }
}
