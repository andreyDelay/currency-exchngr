package com.andrey.currencyexchgr.service.impl;

import com.andrey.currencyexchgr.model.CurrencyRate;
import com.andrey.currencyexchgr.repository.DataBaseCurrencyRepository;
import com.andrey.currencyexchgr.service.CurrencyRateSource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("Postgres")
@RequiredArgsConstructor
public class DataBaseCurrencyRateSource implements CurrencyRateSource {

	private final DataBaseCurrencyRepository repository;

	@Override
	public Optional<CurrencyRate> findByCharCode(String charCode) {
		return repository.findByCharCode(charCode);
	}
}
