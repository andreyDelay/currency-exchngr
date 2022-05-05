package com.andrey.currencyexchgr.dto;

import com.andrey.currencyexchgr.service.model.CurrencyRate;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

@Value
@Builder
public class CurrencyRateDto {

    @Pattern(regexp = "[A-Z]{3}")
    private String charCode;
    @Min(1)
    private double value;

    public CurrencyRate toCurrencyRate() {
        CurrencyRate currencyRate = new CurrencyRate();
        currencyRate.setCharCode(this.charCode);
        currencyRate.setValue(this.value);
        return currencyRate;
    }
}
