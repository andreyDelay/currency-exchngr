package com.andrey.currencyexchgr.exception;

import org.springframework.http.HttpStatus;

public class CurrencyAlreadyExistsException extends ApiException {

	public CurrencyAlreadyExistsException(String message) {
		super("CURRENCY_ALREADY_EXISTS_ERROR", message, HttpStatus.CONFLICT);
	}
}
