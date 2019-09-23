package com.it.cast;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class ServerCommonEsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServerCommonEsApplication.class, args);
	}

}
