package com.example.demo.controller;

import java.math.BigDecimal;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.exception.InvalidCurrencyException;
import com.example.demo.service.CurrencyRateService;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

@Controller
public class CurrencyRateController {

    private final CurrencyRateService currencyRateService;

    public CurrencyRateController(CurrencyRateService currencyRateService) {
        this.currencyRateService = currencyRateService;
    }

    @GetMapping("/currency/rate")
    public ResponseEntity<Object> getCurrencyRate(
            @RequestParam String mainCurrency,
            @RequestParam String moneyCurrency,
            @RequestParam BigDecimal amount
    ) {
        try {
            BigDecimal currencyRate = currencyRateService.getCurrencyRate(
                    mainCurrency,
                    moneyCurrency,
                    amount
            );
            return new ResponseEntity<>(currencyRate, OK);
        } catch (InvalidCurrencyException e) {
            return new ResponseEntity<>(e.getMessage(), BAD_REQUEST);
        }

    }

}
