package com.andrey.currencyexchgr.service;

import com.andrey.currencyexchgr.model.CurrencyRate;

public interface CurrencyService {
    CurrencyRate getCurrencyRateByCode(String currencyCoe);
}

