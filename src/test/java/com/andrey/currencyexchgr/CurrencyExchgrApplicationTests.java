package com.andrey.currencyexchgr;

import com.andrey.currencyexchgr.controller.CurrencyController;
import org.h2.tools.Server;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.sql.Connection;
import java.sql.DriverManager;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestPropertySource("/application-test.yaml")
@ActiveProfiles("test")
//@Sql(value = {"/insert-into-h2.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class CurrencyExchgrApplicationTests {

	@Autowired
	private CurrencyController controller;

	@Test
	void contextLoads() throws Exception {
		assertThat(controller).isNotNull();

		var server = Server.createTcpServer().start();
		Connection connection = DriverManager.getConnection(
				"jdbc:h2:tcp://localhost:9092/mem:currency_db",
				"postgres",
				"postgres");
		org.h2.tools.Server.startWebServer(connection);

		System.out.println(controller.getCurrency("USD"));
	}

}
