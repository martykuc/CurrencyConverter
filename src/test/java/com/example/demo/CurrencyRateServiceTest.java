package com.example.demo;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import com.example.demo.exception.InvalidCurrencyException;
import com.example.demo.model.Currency;
import com.example.demo.service.CurrencyRateService;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class CurrencyRateServiceTest {

    @InjectMocks
    private CurrencyRateService currencyRateService;

    @Test
    public void convertCurrencyStringToEnum() {
        String currencyString = "USD";

        Currency currency = currencyRateService.convertToCurrencyEnum(currencyString);

        Currency expectedCurrency = Currency.USD;

        assertEquals(expectedCurrency, currency);

    }

    @Test(expected = InvalidCurrencyException.class)
    public void throwExceptionOnInvalidCurrency() {
        String currencyString = "ABC";
        currencyRateService.convertToCurrencyEnum(currencyString);
    }

    @Test
    public void testConversionWithDifferentCurrencies() {
        Currency mainCurrency = Currency.EUR;
        Currency moneyCurrency = Currency.DKK;
        BigDecimal amount = BigDecimal.TEN;

        BigDecimal convertedAmount = currencyRateService.calculateRate(
                mainCurrency,
                moneyCurrency,
                amount
        );
        BigDecimal expectedConvertedAmount = new BigDecimal("74.3940");

        assertEquals(expectedConvertedAmount, convertedAmount);
    }

    @Test
    public void testConversionWithSameCurrencies() {
        Currency mainCurrency = Currency.EUR;
        Currency moneyCurrency = Currency.EUR;
        BigDecimal amount = BigDecimal.TEN;

        BigDecimal convertedAmount = currencyRateService.calculateRate(
                mainCurrency,
                moneyCurrency,
                amount
        );
        BigDecimal expectedConvertedAmount = BigDecimal.TEN;

        assertEquals(expectedConvertedAmount, convertedAmount);
    }

    @Test
    public void testConversionWithCurrenciesNotDkk() {
        Currency mainCurrency = Currency.EUR;
        Currency moneyCurrency = Currency.USD;
        BigDecimal amount = BigDecimal.TEN;

        BigDecimal convertedAmount = currencyRateService.calculateRate(
                mainCurrency,
                moneyCurrency,
                amount
        );
        BigDecimal expectedConvertedAmount = new BigDecimal("11.2190");

        assertEquals(expectedConvertedAmount, convertedAmount);
    }

}
