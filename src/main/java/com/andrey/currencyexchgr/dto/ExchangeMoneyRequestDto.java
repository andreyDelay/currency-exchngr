package com.andrey.currencyexchgr.dto;

import lombok.Data;

@Data
public class ExchangeMoneyRequestDto {
	private double rubBalance;
	private String targetCurrencyCode;
}
