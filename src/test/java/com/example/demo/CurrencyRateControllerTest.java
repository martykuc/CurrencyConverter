package com.example.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CurrencyRateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnCorrectCurrencyRate() throws Exception {
        this.mockMvc.perform(get("/currency/rate").param("mainCurrency", "EUR")
                                                  .param("moneyCurrency", "DKK")
                                                  .param("amount", "1"))
                    .andExpect(status().isOk())
                    .andExpect(content().json("7.4394"));
    }

    @Test
    public void shouldReturnEnteredAmountAsCurrencyRate() throws Exception {
        this.mockMvc.perform(get("/currency/rate").param("mainCurrency", "SEK")
                                                  .param("moneyCurrency", "SEK")
                                                  .param("amount", "123.345"))
                    .andExpect(status().isOk())
                    .andExpect(content().json("123.345"));
    }

    @Test
    public void shouldReturnErrorMessage() throws Exception {
        this.mockMvc.perform(get("/currency/rate").param("mainCurrency", "DKK")
                                                  .param("moneyCurrency", "ABC")
                                                  .param("amount", "123.345"))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().string("Invalid currency entered - ABC"));
    }

    @Test
    public void shouldCalculateCurrencyForNonHardcodedValues() throws Exception {
        this.mockMvc.perform(get("/currency/rate").param("mainCurrency", "EUR")
                                                  .param("moneyCurrency", "USD")
                                                  .param("amount", "1"))
                    .andExpect(status().isOk())
                    .andExpect(content().json("1.1219"));
    }

}
