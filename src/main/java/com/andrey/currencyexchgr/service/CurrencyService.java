package com.andrey.currencyexchgr.service;

import com.andrey.currencyexchgr.model.CurrencyRate;

public interface CurrencyService extends GenericService<CurrencyRate, String> {
    CurrencyRate getCurrencyRateByCode(String currencyCoe);
}

