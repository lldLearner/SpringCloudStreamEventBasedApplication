package com.example.source.demo;

import java.util.function.Supplier;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.domain.Transaction;
import com.example.source.services.DataSourceService;

@Configuration
public class CashCardScheduledStream {

	@Bean
	Supplier<Transaction> approvalRequest(DataSourceService dataSourceService) {
		// TODO Auto-generated method stub

		return () -> dataSourceService.getData();
	}

	@Bean
	DataSourceService dataSourceService() {
		return new DataSourceService();
	}
}
