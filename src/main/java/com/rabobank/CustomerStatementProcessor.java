package com.rabobank;

import com.rabobank.property.FileStorageProperties;

import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
		FileStorageProperties.class
})
@Slf4j
public class CustomerStatementProcessor {

	public static void main(String[] args) {
		SpringApplication.run(CustomerStatementProcessor.class, args);
		log.info("Started Spring Boot Application ...");
	}
}
