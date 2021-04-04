package com.transactions.transactionalapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({
		"com.transactions.transactionalapi.domain",
		"com.transactions.transactionalapi.infrastructure",
		"com.transactions.transactionalapi.application",
		"com.transactions.transactionalapi.api",
		"com.transactions.transactionalapi.config"
})
public class TransactionalApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransactionalApiApplication.class, args);
	}

}
