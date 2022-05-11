package com.andrey.currencyexchgr.exception;

import org.springframework.http.HttpStatus;

public class CurrencyNotFoundException extends ApiException {

	public CurrencyNotFoundException(String message) {
		super("CURRENCY_NOT_FOUND_ERROR", message, HttpStatus.NOT_FOUND);
	}
}
