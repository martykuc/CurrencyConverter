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

        if (mainCurrency == moneyCurrency) {
            return amount;
        }

        return mainCurrency.getRate().divide(
                moneyCurrency.getRate(),
                4,
                RoundingMode.UP
        ).multiply(amount);
    }

    public Currency convertToCurrencyEnum(String currency) {
        try {
            return Currency.valueOf(currency);
        } catch (IllegalArgumentException e) {
            throw new InvalidCurrencyException("Invalid currency entered - " + currency);
        }
    }

}
