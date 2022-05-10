package com.andrey.currencyexchgr.dto;

import lombok.Value;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Value
public class ExchangeMoneyRequestDto {

	@NotNull
	@Min(1)
	private Double rubBalance;

	@NotNull
	@Pattern(regexp = "[A-Z]{3}")
	private String targetCurrencyCode;
}
