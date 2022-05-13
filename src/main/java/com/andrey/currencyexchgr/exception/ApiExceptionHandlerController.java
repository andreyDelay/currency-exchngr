package com.andrey.currencyexchgr.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ApiExceptionHandlerController extends ResponseEntityExceptionHandler {

	@ExceptionHandler({CurrencyNotFoundException.class, CurrencyAlreadyExistsException.class,
			ApiBadRequestError.class})
	public ResponseEntity<ApiErrorMessageResponse> handleCurrencyCodeErrors(ApiException e) {
		ApiErrorMessageResponse apiErrorMessageResponse =
				new ApiErrorMessageResponse(e.getCode(), e.getMessage());
		return new ResponseEntity<>(apiErrorMessageResponse, e.getHttpStatus());
	}

	@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
	@ExceptionHandler({Exception.class})
	public ApiErrorMessageResponse generalHandler(Exception e) {
		return new ApiErrorMessageResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
	}
}
