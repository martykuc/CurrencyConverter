package com.example.demo.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.stereotype.Service;

import com.example.demo.exception.InvalidCurrencyException;
import com.example.demo.model.Currency;
import com.example.demo.utils.CurrencyConverter;

@Service
public class CurrencyRateService implements CurrencyConverter {

    public BigDecimal getCurrencyRate(
            String mainCurrencyString,
            String moneyCurrencyString,
            BigDecimal amount
    ) {
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
            calculatedCurrencyRate = mainCurrency.getRate().divide(
                    BigDecimal.valueOf(100),
                    4,
                    RoundingMode.UP
            ).multiply(amount);
        } else {
            calculatedCurrencyRate = mainCurrency.getRate().divide(
                    moneyCurrency.getRate(),
                    4,
                    RoundingMode.UP
            ).multiply(amount);
        }

        return calculatedCurrencyRate;
    }

    public Currency convertToCurrencyEnum(String currency) {
        try {
            return Currency.valueOf(currency);
        } catch (IllegalArgumentException e) {
            throw new InvalidCurrencyException("Invalid currency entered - " + currency);
        }
    }

}
