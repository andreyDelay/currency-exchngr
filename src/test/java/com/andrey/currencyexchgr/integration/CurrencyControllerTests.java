package com.andrey.currencyexchgr.integration;

import com.andrey.currencyexchgr.controller.CurrencyController;
import com.andrey.currencyexchgr.dto.CurrencyRateDto;
import com.andrey.currencyexchgr.exception.CurrencyNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.validation.ConstraintViolationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

	@Value("${controllers.testCharCodeString}")
	private String testCharCodeStringValue;

	@Value("${controllers.testCharCodeValue}")
	private double testCharCodeDoubleValue;

	@Value("${controllers.currencyControllerUrl}")
	private String currencyControllerUrl;

	private CurrencyRateDto createTestCurrencyDto() {
		return CurrencyRateDto.builder()
				.charCode(testCharCodeStringValue)
				.value(testCharCodeDoubleValue)
				.build();
	}

	private String getTestCurrencyDtoAsJSON(CurrencyRateDto currencyRateDto) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(currencyRateDto);
	}

	@Test
	void contextLoadsControllerAvailable() {
		assertThat(controller).isNotNull();
		assertThat(mockMvc).isNotNull();
	}
	//POST
	@Test
	void shouldSaveCurrency() throws Exception {
		CurrencyRateDto testCurrencyRateDto = createTestCurrencyDto();
		String jsonCurrencyRepresentation = getTestCurrencyDtoAsJSON(testCurrencyRateDto);

		mockMvc.perform(post(currencyControllerUrl)
						.contentType(MediaType.APPLICATION_JSON)
						.content(jsonCurrencyRepresentation))
				.andDo(print())
				.andExpectAll(
						status().isCreated(),
						content().contentType(MediaType.APPLICATION_JSON),
						jsonPath("$.charCode").value(testCharCodeStringValue),
						jsonPath("$.value").value(testCharCodeDoubleValue));
	}

	@Test
	void wrongCurrencyCodeShouldFailValidation() throws Exception {
		CurrencyRateDto currencyWithWrongCharCode = CurrencyRateDto.builder()
				.charCode("TEST")
				.value(testCharCodeDoubleValue)
				.build();
		String jsonCurrencyRepresentation = getTestCurrencyDtoAsJSON(currencyWithWrongCharCode);

		mockMvc.perform(post(currencyControllerUrl)
						.contentType(MediaType.APPLICATION_JSON)
						.content(jsonCurrencyRepresentation))
				.andDo(print())
				.andExpect(status().isBadRequest())
				.andExpect(result ->
						assertThat(result.getResolvedException())
								.isInstanceOf(MethodArgumentNotValidException.class))
				.andReturn();
	}

	@Test
	void wrongCurrencyValueShouldFailValidation() throws Exception {
		CurrencyRateDto currencyWithWrongCharCode = CurrencyRateDto.builder()
				.charCode("TBA")
				.value(0.0)
				.build();
		String jsonCurrencyRepresentation = getTestCurrencyDtoAsJSON(currencyWithWrongCharCode);

		mockMvc.perform(post(currencyControllerUrl)
						.contentType(MediaType.APPLICATION_JSON)
						.content(jsonCurrencyRepresentation))
				.andDo(print())
				.andExpect(status().isBadRequest())
				.andExpect(result ->
						assertThat(result.getResolvedException())
								.isInstanceOf(MethodArgumentNotValidException.class))
				.andReturn();
	}

	@Test
	void shouldReturnApiExceptionWhenSaveIfAlreadyExists() throws Exception {
		CurrencyRateDto testCurrencyRateDto = createTestCurrencyDto();
		String jsonCurrencyRepresentation = getTestCurrencyDtoAsJSON(testCurrencyRateDto);

		mockMvc.perform(post(currencyControllerUrl)
						.contentType(MediaType.APPLICATION_JSON)
						.content(jsonCurrencyRepresentation))
				.andDo(print())
				.andExpect(status().isConflict())
				.andExpect(jsonPath("$.message")
						.value(String.format("Currency, with specified code - '%s', already exists", testCharCodeStringValue)))
				.andExpect(jsonPath("$.code").value("CURRENCY_ALREADY_EXISTS_ERROR"));
	}
	//GET
	@Test
	void shouldReturnCurrencyDtoByCurrencyCode() throws Exception {
		mockMvc.perform(get(currencyControllerUrl + "/{charCode}", "USD"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.charCode").value("USD"));
	}

	@Test
	void shouldReturnApiExceptionIfCurrencyCodeNotExist() throws Exception {
		String notExistingCharCode = "QQQ";

		mockMvc.perform(get(currencyControllerUrl + "/{charCode}", notExistingCharCode))
				.andDo(print())
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.message")
						.value(String.format("Currency code %s not found", notExistingCharCode)))
				.andExpect(result ->
						assertThat(result.getResolvedException()).isInstanceOf(CurrencyNotFoundException.class));
	}

	@Test
	void getMethodShouldFailValidation() throws Exception {
		String wrongCharCode = "WRONG CODE";

		mockMvc.perform(get(currencyControllerUrl + "/{charCode}", wrongCharCode))
				.andDo(print())
				.andExpect(status().isServiceUnavailable())
				.andExpect(result -> assertThat(result.getResolvedException())
						.isInstanceOf(ConstraintViolationException.class));
	}
}
