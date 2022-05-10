package com.andrey.currencyexchgr.controller;

import com.andrey.currencyexchgr.dto.ConvertedCurrencyDto;
import com.andrey.currencyexchgr.dto.ExchangeMoneyRequestDto;
import com.andrey.currencyexchgr.service.ExchangeCurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/convert")
@RequiredArgsConstructor
public class ExchangeCurrencyController {

    private final ExchangeCurrencyService exchangeService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ConvertedCurrencyDto convert(@Valid @RequestBody ExchangeMoneyRequestDto exchangeMoneyRequestDto) {
        return exchangeService.exchangeCurrency(exchangeMoneyRequestDto);
    }
}
