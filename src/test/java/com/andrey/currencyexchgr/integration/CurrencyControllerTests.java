package com.andrey.currencyexchgr.integration;

import com.andrey.currencyexchgr.controller.CurrencyController;
import com.andrey.currencyexchgr.dto.CurrencyRateDto;
import com.andrey.currencyexchgr.exception.CurrencyNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.yaml")
@ActiveProfiles("test")
@ComponentScan(lazyInit = true)
class CurrencyControllerTests {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private CurrencyController controller;

	private final String testCharCode = "TBA";
	private final double testCharCodeValue = 77.0;

	private CurrencyRateDto getTestCurrencyDto() {
		return CurrencyRateDto.builder()
				.charCode(testCharCode)
				.value(testCharCodeValue)
				.build();
	}

	@Test
	void contextLoadsControllerAvailable() {
		assertThat(controller).isNotNull();
		assertThat(mockMvc).isNotNull();
	}

	@Test
	void shouldSaveCurrency() throws Exception {
		CurrencyRateDto currencyToBeSaved = getTestCurrencyDto();
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonCurrencyRepresentation = objectMapper.writeValueAsString(currencyToBeSaved);

		mockMvc.perform(post("/api/v1/currency")
						.contentType(MediaType.APPLICATION_JSON)
						.content(jsonCurrencyRepresentation))
				.andDo(print())
				.andExpectAll(
						status().isCreated(),
						content().contentType(MediaType.APPLICATION_JSON),
						jsonPath("$.charCode").value(testCharCode),
						jsonPath("$.value").value(testCharCodeValue));
	}

	@Test
	void shouldReturnApiExceptionWhenSaveIfAlreadyExists() throws Exception {
		CurrencyRateDto targetCurrency;
		try {
			targetCurrency = controller.getCurrency(testCharCode);
		} catch (CurrencyNotFoundException e) {
			targetCurrency = getTestCurrencyDto();
			controller.addCurrency(targetCurrency);
		}
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonCurrencyRepresentation = objectMapper.writeValueAsString(targetCurrency);

		mockMvc.perform(post("/api/v1/currency")
						.contentType(MediaType.APPLICATION_JSON)
						.content(jsonCurrencyRepresentation))
				.andDo(print())
				.andExpect(status().isConflict())
				.andExpect(jsonPath("$.message")
						.value(String.format("Currency, with specified code - '%s', already exists", testCharCode)))
				.andExpect(jsonPath("$.code").value("CURRENCY_ALREADY_EXISTS_ERROR"));
	}

	@Test
	void shouldReturnSavedCurrency() throws Exception {
		CurrencyRateDto currencyToBeSaved = CurrencyRateDto.builder().charCode("TEST").value(55.0).build();
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonCurrencyRepresentation = objectMapper.writeValueAsString(currencyToBeSaved);
		mockMvc.perform(post("/api/v1/currency")
						.contentType(MediaType.APPLICATION_JSON)
						.content(jsonCurrencyRepresentation))
				.andDo(print())
				.andExpect(status().isBadRequest())
				.andExpect(result ->
						assertThat(result.getResolvedException())
								.isInstanceOf(MethodArgumentNotValidException.class))
				.andReturn();
	}

}
