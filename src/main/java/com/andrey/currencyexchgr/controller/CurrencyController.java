package com.andrey.currencyexchgr.controller;

import com.andrey.currencyexchgr.dto.ConvertedCurrencyDto;
import com.andrey.currencyexchgr.dto.ExchangeMoneyRequestDto;
import com.andrey.currencyexchgr.service.ExchangeCurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class CurrencyController {

    private final ExchangeCurrencyService exchangeService;

    @PostMapping(path = "convert/", consumes = "application/json")
    public ResponseEntity<ConvertedCurrencyDto> convert(
            @Valid @RequestBody ExchangeMoneyRequestDto exchangeMoneyRequestDto) {
        ConvertedCurrencyDto convertedCurrencyDto = exchangeService.exchangeCurrency(exchangeMoneyRequestDto);
        return new ResponseEntity<>(convertedCurrencyDto, HttpStatus.OK);
    }
}
