package com.andrey.currencyexchgr.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandlerController extends ResponseEntityExceptionHandler {

	@ExceptionHandler({CurrencyNotFoundException.class, CurrencyAlreadyExistsException.class})
	public ResponseEntity<ErrorDto> handleApiRequestException(Exception e) {
		ErrorDto responseError = new ErrorDto(e.getMessage());
		return new ResponseEntity<>(responseError, HttpStatus.BAD_REQUEST);
	}
}
