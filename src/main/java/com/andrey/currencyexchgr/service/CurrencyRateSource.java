package com.andrey.currencyexchgr.service;

import com.andrey.currencyexchgr.model.CurrencyRate;

import java.util.Optional;

public interface CurrencyRateSource {

	Optional<CurrencyRate> findByCharCode(String charCode);
}
