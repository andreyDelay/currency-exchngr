package com.andrey.currencyexchgr.integration;

import com.andrey.currencyexchgr.controller.CurrencyController;
import com.andrey.currencyexchgr.dto.CurrencyRateDto;
import com.andrey.currencyexchgr.dto.UpdateCurrencyDto;
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
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

	@Value("${controllers.notValidCharCode}")
	private String notValidCharCode;

	@Value("${controllers.notValidRateValue}")
	private double notValidRateValue;

	@Value("${controllers.notExistingCharCode}")
	private String notExistingCharCode;

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

	private String getTestCurrencyDtoAsJSON(UpdateCurrencyDto updateCurrencyDto) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(updateCurrencyDto);
	}

	@Test
	void contextLoadsControllerAvailable() {
		assertThat(controller).isNotNull();
		assertThat(mockMvc).isNotNull();
	}
	//POST
	@Test
	void postMethodShouldSaveCurrency() throws Exception {
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
	void postMethodShouldFailValidationWhenNotValidCharCode() throws Exception {
		CurrencyRateDto currencyWithWrongCharCode = CurrencyRateDto.builder()
				.charCode(notValidCharCode)
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
	void putMethodShouldFailValidationWhenNotValidRateValue() throws Exception {
		CurrencyRateDto currencyWithWrongCharCode = CurrencyRateDto.builder()
				.charCode(testCharCodeStringValue)
				.value(notValidRateValue)
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
	void putMethodShouldReturnApiExceptionWhenSaveIfAlreadyExists() throws Exception {
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
	void getMethodShouldReturnCurrencyDtoByCurrencyCode() throws Exception {
		mockMvc.perform(get(currencyControllerUrl + "/{charCode}", "USD"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.charCode").value("USD"));
	}

	@Test
	void getMethodShouldReturnApiExceptionIfCurrencyCodeNotExist() throws Exception {
		mockMvc.perform(get(currencyControllerUrl + "/{charCode}", notExistingCharCode))
				.andDo(print())
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.message")
						.value(String.format("Currency code %s not found", notExistingCharCode)))
				.andExpect(result ->
						assertThat(result.getResolvedException()).isInstanceOf(CurrencyNotFoundException.class));
	}

	@Test
	void getMethodShouldFailValidationWhenNotValidCharCode() throws Exception {
		mockMvc.perform(get(currencyControllerUrl + "/{charCode}", notValidCharCode))
				.andDo(print())
				.andExpect(status().isServiceUnavailable())
				.andExpect(result -> assertThat(result.getResolvedException())
						.isInstanceOf(ConstraintViolationException.class));
	}

	@Test
	void getMethodShouldReturnCollection() throws Exception {
		mockMvc.perform(get(currencyControllerUrl))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.size()", greaterThanOrEqualTo(0)));
	}
	//PUT
	@Test
	void putMethodShouldFailConstraintValidation() throws Exception {
		//when wrong rate value
		UpdateCurrencyDto currencyDto = new UpdateCurrencyDto();
		currencyDto.setValue(notValidRateValue);
		String jsonCurrencyRepresentation = getTestCurrencyDtoAsJSON(currencyDto);

		mockMvc.perform(put(currencyControllerUrl + "/" + testCharCodeStringValue)
						.contentType(MediaType.APPLICATION_JSON)
						.content(jsonCurrencyRepresentation))
				.andDo(print())
				.andExpect(status().isBadRequest())
				.andExpect(result ->
						assertThat(result.getResolvedException())
								.isInstanceOf(MethodArgumentNotValidException.class));

		//when wrong char code
		currencyDto.setValue(testCharCodeDoubleValue);
		jsonCurrencyRepresentation = getTestCurrencyDtoAsJSON(currencyDto);

		mockMvc.perform(put(currencyControllerUrl + "/" + notValidCharCode)
						.contentType(MediaType.APPLICATION_JSON)
						.content(jsonCurrencyRepresentation))
				.andDo(print())
				.andExpect(status().isServiceUnavailable())
				.andExpect(result ->
						assertThat(result.getResolvedException())
								.isInstanceOf(ConstraintViolationException.class));
	}

	@Test
	void putMethodShouldUpdateCurrency() throws Exception {
		String existingCharCode = "USD";
		double newRateValue = 111.0;
		UpdateCurrencyDto updateCurrencyDto = new UpdateCurrencyDto();
		updateCurrencyDto.setValue(newRateValue);
		String jsonCurrencyRepresentation = getTestCurrencyDtoAsJSON(updateCurrencyDto);

		mockMvc.perform(put(currencyControllerUrl + "/" + existingCharCode)
						.contentType(MediaType.APPLICATION_JSON)
						.content(jsonCurrencyRepresentation))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.value", equalTo(newRateValue)))
				.andExpect(jsonPath("$.charCode", equalTo(existingCharCode)));
	}

	@Test
	void putMethodShouldReturnApiExceptionWhenCurrencyCodeNotFound() throws Exception {
		double newRateValue = 111.0;
		UpdateCurrencyDto updateCurrencyDto = new UpdateCurrencyDto();
		updateCurrencyDto.setValue(newRateValue);
		String jsonCurrencyRepresentation = getTestCurrencyDtoAsJSON(updateCurrencyDto);

		mockMvc.perform(put(currencyControllerUrl + "/" + notExistingCharCode)
						.contentType(MediaType.APPLICATION_JSON)
						.content(jsonCurrencyRepresentation))
				.andDo(print())
				.andExpect(status().isNotFound())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.message",
						equalTo(String.format("Currency code %s not found", notExistingCharCode))))
				.andExpect(result ->
						assertThat(result.getResolvedException())
								.isInstanceOf(CurrencyNotFoundException.class));
	}

	//DELETE
	@Test
	void deleteMethodShouldDeleteCurrency() throws Exception {
		String existingCharCode = "EUR";

		mockMvc.perform(delete(currencyControllerUrl + "/" + existingCharCode))
				.andDo(print())
				.andExpect(status().isNoContent());
	}

	@Test
	void deleteMethodShouldReturnApiExceptionWhenCharCodeNotExist() throws Exception {
		mockMvc.perform(delete(currencyControllerUrl + "/" + notExistingCharCode))
				.andDo(print())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.message",
						equalTo(String.format("Currency code %s not found", notExistingCharCode))))
				.andExpect(result ->
						assertThat(result.getResolvedException())
								.isInstanceOf(CurrencyNotFoundException.class));
	}

	@Test
	void deleteMethodShouldFailValidationWhenNotValidCurrencyCode() throws Exception {
		mockMvc.perform(delete(currencyControllerUrl + "/" + notValidCharCode))
				.andDo(print())
				.andExpect(status().isServiceUnavailable())
				.andExpect(result ->
						assertThat(result.getResolvedException())
								.isInstanceOf(ConstraintViolationException.class));
	}
}
