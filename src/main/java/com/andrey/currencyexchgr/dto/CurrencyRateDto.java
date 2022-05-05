package com.andrey.currencyexchgr.dto;

import com.andrey.currencyexchgr.service.model.CurrencyRate;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CurrencyRateDto {
    private String charCode;
    private double value;

    public CurrencyRate toCurrencyRate() {
        CurrencyRate currencyRate = new CurrencyRate();
        currencyRate.setCharCode(this.charCode);
        currencyRate.setValue(this.value);
        return currencyRate;
    }
}
