package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.binder.test.InputDestination;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;

import com.example.domain.CashCard;
import com.example.domain.Transaction;
import com.example.processor.demo.CashCardSpringCloudStreamProcessorApplication;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;

@SpringBootTest(classes = CashCardSpringCloudStreamProcessorApplication.class)
@Import(TestChannelBinderConfiguration.class)
public class CashCardSpringCloudStreamProcessorApplicationTests {

	@Test
	void shouldEnrichTheTransactionFromSource(@Autowired InputDestination inputDestination,
			@Autowired OutputDestination outputDestination) throws StreamReadException, DatabindException, IOException {
		Transaction transaction = new Transaction(1L, new CashCard(12L, "sarah1", 12345678.99));
		Message<Transaction> message = MessageBuilder.withPayload(transaction).build();
		inputDestination.send(message, "enrichTransaction-in-0");

		Message<byte[]> receievedTransaction = outputDestination.receive(10000, "enrichTransaction-out-0");
		assertThat(receievedTransaction).isNotNull();
	}
}
