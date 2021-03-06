package com.andrey.currencyexchgr.repository;

import com.andrey.currencyexchgr.model.CurrencyRate;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface DataBaseCurrencyRepository extends CrudRepository<CurrencyRate, Long> {

	List<CurrencyRate> findAll();

	Optional<CurrencyRate> findByCharCode(String charCode);

}
