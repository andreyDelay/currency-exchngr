package com.andrey.currencyexchgr.repository;

import com.andrey.currencyexchgr.config.FeignConfig;
import com.andrey.currencyexchgr.model.CurrencyRate;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Optional;

@FeignClient(value = "API-repository", url = "${api.url}", configuration = FeignConfig.class)
public interface ApiCurrencyRepository {

	@RequestMapping(method = RequestMethod.GET, value = "rates/{charCode}")
	Optional<CurrencyRate> findByCurrencyCode(@PathVariable("charCode") String charCode);
}
