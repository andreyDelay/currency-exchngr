package com.andrey.currencyexchgr.service.impl;

import com.andrey.currencyexchgr.dto.CurrencyRateDto;
import com.andrey.currencyexchgr.dto.CurrencyRatesDto;
import com.andrey.currencyexchgr.dto.UpdateCurrencyDto;
import com.andrey.currencyexchgr.model.CurrencyRate;
import com.andrey.currencyexchgr.repository.DataBaseCurrencyRepository;
import com.andrey.currencyexchgr.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("DB-oriented-service")
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DataBaseCurrencyServiceImpl implements CurrencyService {

    private final DataBaseCurrencyRepository repository;

    @Override
    public CurrencyRateDto getCurrencyRateByCode(String currencyCoe) {
        CurrencyRate currencyRate = repository.findByCharCode(currencyCoe)
                .orElseThrow(() -> new RuntimeException("Not found"));
        return CurrencyRateDto.builder()
                .charCode(currencyRate.getCharCode())
                .value(currencyRate.getValue())
                .build();
    }

    @Override
    @Transactional
    public CurrencyRateDto save(CurrencyRateDto currencyRateDto) {
        repository.save(currencyRateDto.toCurrencyRate());
        return currencyRateDto;
    }

    @Override
    public CurrencyRatesDto findAll() {
        List<CurrencyRateDto> result = repository.findAll()
                .stream()
                .map(value ->
                        CurrencyRateDto.builder()
                                .value(value.getValue())
                                .charCode(value.getCharCode())
                                .build())
                .toList();
        return new CurrencyRatesDto(result);
    }

    @Override
    @Transactional
    public CurrencyRateDto update(String charCode, UpdateCurrencyDto updateCurrencyDto) {
        CurrencyRate currencyRate = repository.findByCharCode(charCode)
                .orElseThrow(() -> new RuntimeException("Not found"));

        currencyRate.setValue(updateCurrencyDto.getValue());
        repository.save(currencyRate);
        return CurrencyRateDto.builder()
                .charCode(charCode)
                .value(updateCurrencyDto.getValue())
                .build();
    }

    @Override
    @Transactional
    public void delete(String charCode) {
        repository.findByCharCode(charCode)
                .ifPresentOrElse(repository::delete, () -> {
                    throw new RuntimeException("Not found");
                });
    }

}
