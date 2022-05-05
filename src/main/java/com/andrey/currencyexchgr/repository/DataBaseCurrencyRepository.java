package com.andrey.currencyexchgr.repository;

import com.andrey.currencyexchgr.model.CurrencyRate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DataBaseCurrencyRepository extends JpaRepository<CurrencyRate, String> {

	Optional<CurrencyRate> findByCharCode(String charCode);

}
