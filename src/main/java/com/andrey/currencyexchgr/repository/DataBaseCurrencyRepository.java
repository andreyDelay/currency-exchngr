package com.andrey.currencyexchgr.repository;

import com.andrey.currencyexchgr.model.CurrencyRate;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("Postgres-repository")
public interface DataBaseCurrencyRepository extends CrudRepository<CurrencyRate, Long>, ApiCurrencyRepository {

	List<CurrencyRate> findAll();

	@Override
	Optional<CurrencyRate> findByCharCode(String charCode);

}
