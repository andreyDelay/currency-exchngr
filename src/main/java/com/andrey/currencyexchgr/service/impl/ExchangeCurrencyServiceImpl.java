package com.andrey.currencyexchgr.service.impl;

import com.andrey.currencyexchgr.dto.ConvertedCurrencyDto;
import com.andrey.currencyexchgr.dto.ExchangeMoneyRequestDto;
import com.andrey.currencyexchgr.exception.CurrencyNotFoundException;
import com.andrey.currencyexchgr.model.CurrencyRate;
import com.andrey.currencyexchgr.service.CurrencyRateSource;
import com.andrey.currencyexchgr.service.ExchangeCurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExchangeCurrencyServiceImpl implements ExchangeCurrencyService {

    @Qualifier("API")
    private final CurrencyRateSource currencyRateSource;

    @Override
    public ConvertedCurrencyDto exchangeCurrency(ExchangeMoneyRequestDto exchangeMoneyRequestDto) {
        String charCode = exchangeMoneyRequestDto.getTargetCurrencyCode();
        CurrencyRate currencyRate = currencyRateSource.findByCharCode(charCode)
                .orElseThrow(() -> new CurrencyNotFoundException(
                        String.format("Currency code %s not found", charCode)));

        double exchangeResult =
                exchangeCurrency(currencyRate.getValue(), exchangeMoneyRequestDto.getRubBalance());
        return ConvertedCurrencyDto.builder()
                .exchangeResult(exchangeResult)
                .currencyRate(currencyRate.getValue())
                .rubBalance(exchangeMoneyRequestDto.getRubBalance())
                .targetCurrencyCode(charCode)
                .build();
    }

    private double exchangeCurrency(double rate, double rubBalance) {
        return rubBalance / rate;
    }
}
