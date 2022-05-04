package com.andrey.currencyexchgr.repository;

import com.andrey.currencyexchgr.model.CurrencyRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DataBaseCurrencyRepository extends JpaRepository<CurrencyRate, String> {

/*	@Query(value = "SELECT " +
						"id, currency_code, currency_rate " +
					"FROM CurrencyRate cr " +
					"WHERE cr.charCode = ?1", nativeQuery = true)*/
	@Query(value = "SELECT cr FROM CurrencyRate cr WHERE cr.charCode = ?1")
	CurrencyRate findRateByCurrencyCode(String currencyCode);

}
