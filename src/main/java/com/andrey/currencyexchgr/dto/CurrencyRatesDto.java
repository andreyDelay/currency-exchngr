package com.andrey.currencyexchgr.dto;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.List;

@Value
@AllArgsConstructor
public class CurrencyRatesDto {

    private List<CurrencyRateDto> currencyRates;

}
