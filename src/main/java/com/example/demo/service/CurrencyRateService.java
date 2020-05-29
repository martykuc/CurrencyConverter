package com.example.demo.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.demo.exception.InvalidCurrencyException;
import com.example.demo.model.Currency;
import com.example.demo.utils.CurrencyConverter;

@Service
public class CurrencyRateService implements CurrencyConverter {

    @Value("#{${currency.rate}}")
    private Map<Currency, BigDecimal> rates;

    public BigDecimal getCurrencyRate(
            String mainCurrencyString,
            String moneyCurrencyString,
            BigDecimal amount
    ) throws InvalidCurrencyException {

        Currency mainCurrency = convertToCurrencyEnum(mainCurrencyString);
        Currency moneyCurrency = convertToCurrencyEnum(moneyCurrencyString);
        return calculateRate(mainCurrency, moneyCurrency, amount);
    }

    @Override
    public BigDecimal calculateRate(
            Currency mainCurrency,
            Currency moneyCurrency,
            BigDecimal amount
    ) {
        BigDecimal calculatedCurrencyRate = amount;

        if (mainCurrency == moneyCurrency) {
            return calculatedCurrencyRate;
        }

        if (moneyCurrency == Currency.DKK) {
            BigDecimal mainCurrencyRate = rates.get(mainCurrency);
            calculatedCurrencyRate = mainCurrencyRate.divide(
                    BigDecimal.valueOf(100),
                    4,
                    RoundingMode.UP
            ).multiply(amount);
        } else {
            BigDecimal mainCurrencyDkkRate = rates.get(mainCurrency);
            BigDecimal moneyCurrencyDkkRate = rates.get(moneyCurrency);
            calculatedCurrencyRate = mainCurrencyDkkRate.divide(
                    moneyCurrencyDkkRate,
                    4,
                    RoundingMode.UP
            ).multiply(amount);
        }

        return calculatedCurrencyRate;
    }

    public Currency convertToCurrencyEnum(String currency) throws InvalidCurrencyException {
        try {
            return Currency.valueOf(currency);
        } catch (IllegalArgumentException e) {
            throw new InvalidCurrencyException("Invalid currency entered - " + currency);
        }
    }

}
