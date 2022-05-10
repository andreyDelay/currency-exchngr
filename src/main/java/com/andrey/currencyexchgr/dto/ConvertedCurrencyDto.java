package com.andrey.currencyexchgr.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Value;

@Value
@Builder
public class ConvertedCurrencyDto {
	private double rubBalance;
	private String targetCurrencyCode;
	private double currencyRate;
	private double exchangeResult;
}
