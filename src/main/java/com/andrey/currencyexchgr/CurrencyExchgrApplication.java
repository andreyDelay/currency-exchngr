package com.andrey.currencyexchgr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CurrencyExchgrApplication {

	public static void main(String[] args) {
		SpringApplication.run(CurrencyExchgrApplication.class, args);
	}

}
