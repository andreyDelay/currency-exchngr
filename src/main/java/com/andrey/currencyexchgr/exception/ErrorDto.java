package com.andrey.currencyexchgr.exception;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class ErrorDto {

	private String errorMessage;
}
