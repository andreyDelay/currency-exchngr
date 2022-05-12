package com.andrey.currencyexchgr.exception;

import org.springframework.http.HttpStatus;

public class NotSupportedOperationException extends ApiException {

	public NotSupportedOperationException(String message) {
		super("METHOD_NOT_SUPPORTED_BY_SERVER_ERROR", message, HttpStatus.SERVICE_UNAVAILABLE);
	}
}
