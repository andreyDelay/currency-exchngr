package com.andrey.currencyexchgr.service.impl;

import com.andrey.currencyexchgr.model.CurrencyRate;
import com.andrey.currencyexchgr.repository.ApiCurrencyRepository;
import com.andrey.currencyexchgr.service.CurrencyRateSource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("API")
@RequiredArgsConstructor
public class ApiCurrencyRateSource implements CurrencyRateSource {

	private final ApiCurrencyRepository repository;

	@Override
	public Optional<CurrencyRate> findByCharCode(String charCode) {
		return repository.findByCharCode(charCode);
	}
}
