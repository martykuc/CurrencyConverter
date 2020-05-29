package com.example.demo.utils;

import java.math.BigDecimal;

import com.example.demo.model.Currency;

public interface CurrencyConverter {

    BigDecimal calculateRate(Currency mainCurrency, Currency moneyCurrency, BigDecimal amount);

}
