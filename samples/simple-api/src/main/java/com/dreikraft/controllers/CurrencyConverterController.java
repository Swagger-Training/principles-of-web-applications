package com.dreikraft.controllers;

import com.dreikraft.model.Currency;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController()
public class CurrencyConverterController {

    @RequestMapping(
            value = "/currencies",
            method = RequestMethod.GET,
            produces = "application/json")
    @ResponseBody
    public List<Currency> getCurrencies(@RequestParam(name = "name", required = false) String name) {
        return Stream.of(
                new Currency("EUR", "Euro", 1.0),
                new Currency("USD", "US Dollar", 1.1)
        ).collect(Collectors.toList());
    }
}
