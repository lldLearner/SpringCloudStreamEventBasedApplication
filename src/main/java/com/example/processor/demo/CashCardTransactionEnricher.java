package com.example.processor.demo;

import java.util.function.Function;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.domain.EnrichedTransaction;
import com.example.domain.Transaction;
import com.example.processor.services.EnrichmentService;

@Configuration
public class CashCardTransactionEnricher {

    @Bean
    Function<Transaction, EnrichedTransaction> enrichTransaction(EnrichmentService enrichmentService) {
		return transaction -> {
			return enrichmentService.enrichedTransaction(transaction);
		};
	}

    @Bean
    EnrichmentService enrichmentService() {
		return new EnrichmentService();
	}
}
