package com.andrey.currencyexchgr.service.impl;

import com.andrey.currencyexchgr.dto.ConvertedCurrencyDto;
import com.andrey.currencyexchgr.dto.ExchangeMoneyRequestDto;
import com.andrey.currencyexchgr.service.model.CurrencyRate;
import com.andrey.currencyexchgr.service.CurrencyService;
import com.andrey.currencyexchgr.service.ExchangeCurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExchangeCurrencyServiceImpl implements ExchangeCurrencyService {

    @Qualifier("DB-oriented-service")
    private final CurrencyService currencyService;

    @Override
    public ConvertedCurrencyDto exchangeCurrency(ExchangeMoneyRequestDto exchangeMoneyRequestDto) {
        String targetCurrencyCode = exchangeMoneyRequestDto.getTargetCurrencyCode();
        CurrencyRate targetCurrencyData =
                currencyService.getCurrencyRateByCode(targetCurrencyCode)
                        .orElseThrow(() -> new RuntimeException("Currency code not found"));

        double exchangeResult =
                exchangeCurrency(targetCurrencyData.getValue(), exchangeMoneyRequestDto.getRubBalance());
        return ConvertedCurrencyDto.builder()
                .exchangeResult(exchangeResult)
                .currencyRate(targetCurrencyData.getValue())
                .rubBalance(exchangeMoneyRequestDto.getRubBalance())
                .targetCurrencyCode(targetCurrencyCode)
                .build();
    }

    private double exchangeCurrency(double rate, double rubBalance) {
        return rubBalance / rate;
    }
}
