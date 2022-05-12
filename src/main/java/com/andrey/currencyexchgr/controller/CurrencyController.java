package com.andrey.currencyexchgr.controller;

import com.andrey.currencyexchgr.dto.CurrencyRateDto;
import com.andrey.currencyexchgr.dto.CurrencyRatesDto;
import com.andrey.currencyexchgr.dto.UpdateCurrencyDto;
import com.andrey.currencyexchgr.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;


@RestController
@RequestMapping("/api/v1/currency")
@RequiredArgsConstructor
public class CurrencyController {

    @Qualifier("DB-oriented-service")
    private final CurrencyService currencyService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CurrencyRateDto addCurrency(@Valid @RequestBody CurrencyRateDto currencyRateDto) {
        return currencyService.save(currencyRateDto);
    }

    @GetMapping("/{charCode}")
    @ResponseStatus(HttpStatus.OK)
    public CurrencyRateDto getCurrency(@Valid @Pattern(regexp = "[A-Z]{3}")
                                       @PathVariable String charCode) {
        return currencyService.getCurrencyRateByCode(charCode);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CurrencyRatesDto getAllCurrency() {
        return currencyService.findAll();
    }

    @PutMapping("/{charCode}")
    @ResponseStatus(HttpStatus.OK)
    public CurrencyRateDto updateCurrency(@Valid @RequestBody UpdateCurrencyDto updateCurrencyDto,
                                          @Valid @Pattern(regexp = "[A-Z]{3}") @PathVariable String charCode) {
        return currencyService.update(charCode, updateCurrencyDto);
    }

    @DeleteMapping("/{charCode}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCurrency(@Valid @Pattern(regexp = "[A-Z]{3}")
                               @PathVariable String charCode) {
        currencyService.delete(charCode);
    }

}
