package com.andrey.currencyexchgr.service;


import com.andrey.currencyexchgr.dto.CurrencyRateDto;
import com.andrey.currencyexchgr.dto.CurrencyRatesDto;
import com.andrey.currencyexchgr.dto.UpdateCurrencyDto;

public interface OpenFeignCurrencyService extends CurrencyService {

	@Override
	CurrencyRateDto getCurrencyRateByCode(String charCode);

	@Override
	CurrencyRateDto save(CurrencyRateDto currencyRate);

	@Override
	CurrencyRatesDto findAll();

	@Override
	void delete(String charCode);

	@Override
	CurrencyRateDto update(String charCode, UpdateCurrencyDto updateCurrencyDto);
}
