package de.check24.challenge.web.service;

import de.check24.challenge.web.dto.ExchangeRatesResponse;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class CurrencyServiceImpl implements CurrencyService{

    @Override
    @Cacheable("currencyCache")
    public ExchangeRatesResponse getCurrency() {
        RestTemplate restTemplate=new RestTemplate();

        String apiURL = "http://api.exchangeratesapi.io/v1/latest";
        String accessKey = "c1b8abf515ae305e6d8815ad70a108ac";
        String requestURL = apiURL + "?access_key=" + accessKey + "&format=1";

        return restTemplate.getForObject(requestURL, ExchangeRatesResponse.class);
    }

    @Override
    public double convertCurrency(double amount, String fromCurrency, ExchangeRatesResponse response) {

        Map<String, Double> rates = response.getRates();

        if (rates==null || !rates.containsKey(fromCurrency)) {
            throw new IllegalArgumentException("Invalid currencies provided.");
        }

        double fromCurrencyRate = rates.get(fromCurrency);

        if(fromCurrencyRate==0){
            throw new ArithmeticException("Invalid Rate provided");
        }

        double convertedAmount = (amount / fromCurrencyRate);
        return convertedAmount;
    }

}
