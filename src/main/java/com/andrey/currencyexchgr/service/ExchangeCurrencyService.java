package com.andrey.currencyexchgr.service;

import com.andrey.currencyexchgr.dto.ConvertedCurrencyDto;
import com.andrey.currencyexchgr.dto.ExchangeMoneyRequestDto;

public interface ExchangeCurrencyService {
    ConvertedCurrencyDto exchangeCurrency(ExchangeMoneyRequestDto exchangeMoneyRequestDto);
}
