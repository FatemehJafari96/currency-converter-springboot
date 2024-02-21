package de.check24.challenge.web.controller;

import de.check24.challenge.web.dto.ExchangeRatesResponse;
import de.check24.challenge.web.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class ConvertController {
    @Autowired
    private CurrencyService currencyService;
    ExchangeRatesResponse response;

    @GetMapping("/convert")
    public String convertCurrency(Model model){
        response=currencyService.getCurrency();
        model.addAttribute("rates",response.getRates());
        model.addAttribute("amount", 0);
        return "convert";
    }
    @PostMapping("/convert")
    public String convertCurrency(@RequestParam(name = "amount") double amount,
                                  @RequestParam(name = "from") String from,
                                  Model model){
        if(response==null){
            response=currencyService.getCurrency();
        }
        double result = currencyService.convertCurrency(amount,from,response);
        model.addAttribute("rates",response.getRates());
        model.addAttribute("selected", from);
        model.addAttribute("amount", amount);
        model.addAttribute("result",result);
        return "convert";
    }
}
