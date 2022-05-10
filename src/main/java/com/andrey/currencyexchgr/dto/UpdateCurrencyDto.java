package com.andrey.currencyexchgr.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class UpdateCurrencyDto {

	@NotNull
	@Min(1)
	private Double value;
}
