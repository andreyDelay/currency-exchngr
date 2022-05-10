package com.andrey.currencyexchgr.dto;

import lombok.Data;

import javax.validation.constraints.Min;

@Data
public class UpdateCurrencyDto {
	@Min(1)
	private double value;
}
