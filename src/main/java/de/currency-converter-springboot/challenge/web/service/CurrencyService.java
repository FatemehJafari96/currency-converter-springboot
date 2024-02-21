package de.check24.challenge.web.service;

import de.check24.challenge.web.dto.ExchangeRatesResponse;
import org.springframework.stereotype.Service;

@Service
public interface CurrencyService {
    ExchangeRatesResponse getCurrency();
    double convertCurrency(double amount,String fromCurrency,ExchangeRatesResponse response);
}
