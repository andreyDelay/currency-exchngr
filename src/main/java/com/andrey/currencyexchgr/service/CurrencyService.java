package com.andrey.currencyexchgr.service;

import com.andrey.currencyexchgr.dto.CurrencyRateDto;
import com.andrey.currencyexchgr.dto.CurrencyRatesDto;
import com.andrey.currencyexchgr.model.CurrencyRate;

import java.util.Optional;

public interface CurrencyService {

    Optional<CurrencyRate> getCurrencyRateByCode(String currencyCoe);

    void save(CurrencyRateDto currencyRate);

    CurrencyRateDto findById(String charCode);

    CurrencyRatesDto findAll();

    void delete(String charCode);

    CurrencyRateDto update(CurrencyRateDto currencyRateDto);
}

