package com.andrey.currencyexchgr.dto;

import com.andrey.currencyexchgr.model.CurrencyRate;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Value
@Builder
public class CurrencyRateDto {

    @NotNull
    @Pattern(regexp = "[A-Z]{3}")
    private String charCode;

    @NotNull
    @Min(1)
    private Double value;

    public CurrencyRate toCurrencyRate() {
        CurrencyRate currencyRate = new CurrencyRate();
        currencyRate.setCharCode(this.charCode);
        currencyRate.setValue(this.value);
        return currencyRate;
    }
}
