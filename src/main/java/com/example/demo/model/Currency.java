package com.example.demo.model;

import java.math.BigDecimal;

public enum Currency {
    EUR(BigDecimal.valueOf(743.94)),
    USD(BigDecimal.valueOf(663.11)),
    GBP(BigDecimal.valueOf(852.85)),
    SEK(BigDecimal.valueOf(76.10)),
    NOK(BigDecimal.valueOf(78.40)),
    CHF(BigDecimal.valueOf(683.58)),
    JPY(BigDecimal.valueOf(75.74)),
    DKK(BigDecimal.valueOf(100));

    private BigDecimal rate;

    Currency(BigDecimal rate) {
        this.rate = rate;
    }

    public BigDecimal getRate() {
        return rate;
    }
}
