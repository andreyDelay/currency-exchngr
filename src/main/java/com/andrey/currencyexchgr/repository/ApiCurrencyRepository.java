package com.andrey.currencyexchgr.repository;

import com.andrey.currencyexchgr.config.FeignConfig;
import com.andrey.currencyexchgr.model.CurrencyRate;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient(value = "API-repository", url = "${api.url}", configuration = FeignConfig.class)
public interface ApiCurrencyRepository {

	@GetMapping("/{charCode}")
	Optional<CurrencyRate> findByCurrencyCode(@PathVariable String charCode);
}
