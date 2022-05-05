package com.andrey.currencyexchgr.service.impl;

import com.andrey.currencyexchgr.model.CurrencyRate;
import com.andrey.currencyexchgr.repository.DataBaseCurrencyRepository;
import com.andrey.currencyexchgr.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("DB-oriented-service")
@RequiredArgsConstructor
public class DataBaseCurrencyServiceImpl implements CurrencyService {

    private final DataBaseCurrencyRepository repository;

    @Override
    public Optional<CurrencyRate> getCurrencyRateByCode(String currencyCoe) {
        return repository.findByCharCode(currencyCoe);
    }
}
