package com.andrey.currencyexchgr.exception;

public class CurrencyAlreadyExistsException extends RuntimeException {

	public CurrencyAlreadyExistsException(String message) {
		super(message);
	}

	public CurrencyAlreadyExistsException(String message, Throwable cause) {
		super(message, cause);
	}
}
