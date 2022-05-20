package com.andrey.currencyexchgr.h2config;

import org.h2.tools.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.SQLException;

/*@Configuration*/
public class H2Configuration {

	@Bean
	public Server server() throws SQLException {
		return Server.createTcpServer().start();
	}
}
