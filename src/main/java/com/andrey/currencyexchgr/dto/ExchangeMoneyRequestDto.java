package com.andrey.currencyexchgr.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

@Data
public class ExchangeMoneyRequestDto {
	@Min(1)
	private double rubBalance;
	@Pattern(regexp = "[A-Z]{3}")
	private String targetCurrencyCode;
}
