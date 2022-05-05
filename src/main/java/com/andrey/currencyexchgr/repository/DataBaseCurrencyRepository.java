package com.andrey.currencyexchgr.repository;

import com.andrey.currencyexchgr.model.CurrencyRate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DataBaseCurrencyRepository extends JpaRepository<CurrencyRate, String> {

	CurrencyRate findByCharCode(String charCode);

}
