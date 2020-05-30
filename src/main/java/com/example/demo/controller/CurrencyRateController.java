package com.example.demo.controller;

import java.math.BigDecimal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.service.CurrencyRateService;

@Controller
public class CurrencyRateController {

    private final CurrencyRateService currencyRateService;

    public CurrencyRateController(CurrencyRateService currencyRateService) {
        this.currencyRateService = currencyRateService;
    }

    @GetMapping("/currency/rate")
    @ResponseBody
    public BigDecimal getCurrencyRate(
            @RequestParam String mainCurrency,
            @RequestParam String moneyCurrency,
            @RequestParam BigDecimal amount
    ) {
        return currencyRateService.getCurrencyRate(
                mainCurrency,
                moneyCurrency,
                amount
        );
    }

}
