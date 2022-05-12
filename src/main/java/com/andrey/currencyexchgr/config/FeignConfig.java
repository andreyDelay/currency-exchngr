package com.andrey.currencyexchgr.config;

import com.andrey.currencyexchgr.utils.FeignResponseStatusDecoder;
import feign.Logger;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;

public class FeignConfig {

	@Bean
	Logger.Level feignLoggerLevel() {
		return Logger.Level.FULL;
	}

	@Bean
	ErrorDecoder errorDecoder() {
		return new FeignResponseStatusDecoder();
	}
}
