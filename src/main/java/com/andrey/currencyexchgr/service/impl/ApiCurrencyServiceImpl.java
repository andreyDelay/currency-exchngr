package com.andrey.currencyexchgr.service.impl;

import com.andrey.currencyexchgr.dto.CurrencyRateDto;
import com.andrey.currencyexchgr.dto.CurrencyRatesDto;
import com.andrey.currencyexchgr.dto.UpdateCurrencyDto;
import com.andrey.currencyexchgr.exception.CurrencyNotFoundException;
import com.andrey.currencyexchgr.exception.NotSupportedOperationException;
import com.andrey.currencyexchgr.model.CurrencyRate;
import com.andrey.currencyexchgr.repository.ApiCurrencyRepository;
import com.andrey.currencyexchgr.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service(value = "API-oriented")
@RequiredArgsConstructor
public class ApiCurrencyServiceImpl implements CurrencyService {

	private final ApiCurrencyRepository repository;

	@Override
	public CurrencyRateDto getCurrencyRateByCode(String charCode) {
		CurrencyRate currencyRate = repository.findByCurrencyCode(charCode)
				.orElseThrow(() -> new CurrencyNotFoundException(
						String.format("Currency code %s not found", charCode)));

		return CurrencyRateDto.builder()
				.charCode(currencyRate.getCharCode())
				.value(currencyRate.getValue())
				.build();
	}

	@Override
	public CurrencyRateDto save(CurrencyRateDto currencyRate) {
		throw new NotSupportedOperationException("The service cannot be used for such purpose right now.");
	}

	@Override
	public CurrencyRatesDto findAll() {
		throw new NotSupportedOperationException("The service cannot be used for such purpose right now.");
	}

	@Override
	public void delete(String charCode) {
		throw new NotSupportedOperationException("The service cannot be used for such purpose right now.");
	}

	@Override
	public CurrencyRateDto update(String charCode, UpdateCurrencyDto updateCurrencyDto) {
		throw new NotSupportedOperationException("The service cannot be used for such purpose right now.");
	}
}
