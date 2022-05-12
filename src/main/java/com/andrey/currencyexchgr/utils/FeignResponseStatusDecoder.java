package com.andrey.currencyexchgr.utils;

import com.andrey.currencyexchgr.exception.ApiBadRequestError;
import com.andrey.currencyexchgr.exception.CurrencyNotFoundException;
import feign.Response;
import feign.codec.ErrorDecoder;

public class FeignResponseStatusDecoder implements ErrorDecoder {

	private ErrorDecoder errorDecoder = new Default();

	@Override
	public Exception decode(String s, Response response) {
		switch (response.status()) {
			case 400 -> throw new ApiBadRequestError("Bad request to API");
			case 404 -> throw new CurrencyNotFoundException("Target currency code not found by API");
			default -> {
				return errorDecoder.decode(s, response);
			}
		}
	}
}
