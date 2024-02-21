package de.check24.challenge.web.controller;

import de.check24.challenge.web.dto.ExchangeRatesResponse;
import de.check24.challenge.web.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * IndexController
 */
@Controller
public class IndexController {


    /**
     * Index endpoint to show the index page
     *
     * @param model Spring's view model
     * @return view name
     */
    @GetMapping({"/", "/index"})
    public String index(Model model) {
        model.addAttribute("title", "CHECK24 coding challenge");
        model.addAttribute("welcome", "Welcome to Check24");
        model.addAttribute("applicationTitle", "Check24 Currency Converter");
        return "index";
    }

}
