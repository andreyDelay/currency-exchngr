package com.andrey.currencyexchgr.dto;

import lombok.Value;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

@Value
public class ExchangeMoneyRequestDto {
	@Min(1)
	private double rubBalance;
	@Pattern(regexp = "[A-Z]{3}")
	private String targetCurrencyCode;
}
