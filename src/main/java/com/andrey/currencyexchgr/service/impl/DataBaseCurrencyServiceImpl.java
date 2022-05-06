package com.andrey.currencyexchgr.service.impl;

import com.andrey.currencyexchgr.dto.CurrencyRateDto;
import com.andrey.currencyexchgr.dto.CurrencyRatesDto;
import com.andrey.currencyexchgr.model.CurrencyRate;
import com.andrey.currencyexchgr.repository.DataBaseCurrencyRepository;
import com.andrey.currencyexchgr.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("DB-oriented-service")
@RequiredArgsConstructor
public class DataBaseCurrencyServiceImpl implements CurrencyService {

    private final DataBaseCurrencyRepository repository;

    @Override
    @Transactional(readOnly = true)
    public Optional<CurrencyRate> getCurrencyRateByCode(String currencyCoe) {
        return repository.findByCharCode(currencyCoe);
    }

    @Override
    @Transactional
    public void save(CurrencyRateDto currencyRateDto) {
        repository.save(currencyRateDto.toCurrencyRate());
    }

    @Override
    @Transactional(readOnly = true)
    public CurrencyRateDto findById(String charCode) {
        Optional<CurrencyRate> byCharCode = repository.findByCharCode(charCode);
        return repository.findByCharCode(charCode)
                .map(response ->
                        CurrencyRateDto.builder()
                        .charCode(response.getCharCode())
                        .value(response.getValue())
                        .build())
                .orElseThrow(() -> new RuntimeException("Not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public CurrencyRatesDto findAll() {
        List<CurrencyRateDto> currencyRateDtoList = new ArrayList<>();
        repository.findAll().forEach(value ->
                currencyRateDtoList.add(
                        CurrencyRateDto.builder()
                        .value(value.getValue())
                        .charCode(value.getCharCode())
                        .build()));
        return new CurrencyRatesDto(currencyRateDtoList);
    }

    @Override
    @Transactional
    public CurrencyRateDto update(CurrencyRateDto currencyRateDto) {
        Optional<CurrencyRate> byCharCode = repository.findByCharCode(currencyRateDto.getCharCode());
        CurrencyRate currencyRate = byCharCode
                .orElseThrow(() -> new RuntimeException("Not found"));
        currencyRate.setValue(currencyRateDto.getValue());
        repository.save(currencyRate);
        return currencyRateDto;
    }

    @Override
    @Transactional
    public void delete(String charCode) {
        Optional<CurrencyRate> targetCurrency = repository.findByCharCode(charCode);
        CurrencyRate currencyRate = targetCurrency
                .orElseThrow(() -> new RuntimeException("Not found"));
        repository.delete(currencyRate);
    }

}
