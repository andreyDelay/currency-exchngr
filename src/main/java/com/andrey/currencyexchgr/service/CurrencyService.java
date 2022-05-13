package com.andrey.currencyexchgr.service;

import com.andrey.currencyexchgr.dto.CurrencyRateDto;
import com.andrey.currencyexchgr.dto.CurrencyRatesDto;
import com.andrey.currencyexchgr.dto.UpdateCurrencyDto;

public interface CurrencyService {

    CurrencyRateDto findByCharCode(String charCode);

    CurrencyRateDto save(CurrencyRateDto currencyRate);

    CurrencyRatesDto findAll();

    void delete(String charCode);

    CurrencyRateDto update(String charCode, UpdateCurrencyDto updateCurrencyDto);
}

