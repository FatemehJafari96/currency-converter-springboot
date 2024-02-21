package de.check24.challenge.web.controller;

import de.check24.challenge.web.dto.ExchangeRatesResponse;
import de.check24.challenge.web.service.CurrencyService;
import de.check24.challenge.web.service.CurrencyServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.validation.support.BindingAwareModelMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class IndexControllerTest {

    @Mock
    private CurrencyService currencyService;

    @InjectMocks
    private CurrencyServiceImpl currencyServiceImpl;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    private RestTemplate restTemplate = mock(RestTemplate.class);

    private IndexController indexController = new IndexController();

    @Test
    @DisplayName("Simple test")
    void index() {
        Model model = new BindingAwareModelMap();
        String result = indexController.index(model);
        assertThat(result).isEqualTo("index");
        assertThat(model.getAttribute("title"))
                .isEqualTo("CHECK24 coding challenge");
    }

    @Test
    @DisplayName("Test convertCurrency method")
    void convertCurrency() {
        double amount = 100.0;
        String fromCurrency = "USD";
        double conversionRate = 1.2345;
        double expectedConvertedAmount = amount / conversionRate;

        ExchangeRatesResponse response = new ExchangeRatesResponse();
        Map<String, Double> rates=new HashMap<>();
        rates.put(fromCurrency, conversionRate);
        response.setRates(rates);

        when(restTemplate.getForObject(anyString(), eq(ExchangeRatesResponse.class))).thenReturn(response);


        double convertedAmount = currencyServiceImpl.convertCurrency(amount, fromCurrency, response);

        assertThat(convertedAmount).isEqualTo(expectedConvertedAmount);

    }


    @Test
    @DisplayName("Test convertCurrency method with invalid currency")
    void convertCurrency_InvalidCurrency() {
        double amount = 100.0;
        String fromCurrency = "INVALID";

        ExchangeRatesResponse response = new ExchangeRatesResponse();

        when(restTemplate.getForObject(anyString(), eq(ExchangeRatesResponse.class))).thenReturn(response);

        assertThatThrownBy(() -> currencyServiceImpl.convertCurrency(amount, fromCurrency, response))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Invalid currencies provided.");
    }
    @Test
    @DisplayName("Test convertCurrency method with invalid rate")
    void convertCurrency_InvalidRate() {
        double amount = 100.0;
        String fromCurrency = "USD";
        double conversionRate = 0;

        ExchangeRatesResponse response = new ExchangeRatesResponse();
        Map<String, Double> rates=new HashMap<>();
        rates.put(fromCurrency, conversionRate);
        response.setRates(rates);

        when(restTemplate.getForObject(anyString(), eq(ExchangeRatesResponse.class))).thenReturn(response);

        assertThatThrownBy(() -> currencyServiceImpl.convertCurrency(amount, fromCurrency, response))
                .isInstanceOf(ArithmeticException.class)
                .hasMessage("Invalid Rate provided");
    }


}

