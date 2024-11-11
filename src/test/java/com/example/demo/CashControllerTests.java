package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;

import com.example.domain.CashCard;
import com.example.domain.Transaction;
import com.example.source.demo.CashCardOnDemandStream;
import com.example.source.demo.CashCardSpringCloudStreamApplication;
import com.example.source.services.DataSourceService;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(classes = CashCardSpringCloudStreamApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@Import({TestChannelBinderConfiguration.class, CashCardOnDemandStream.class})
public class CashControllerTests {
	@Autowired
	TestRestTemplate testRestTemplate;

	@MockBean
	private DataSourceService dataSourceService;
	
	@Test
	void cashcard_stream_bridge(@Autowired OutputDestination outputDestination) throws StreamReadException, DatabindException, IOException {
		Transaction testTransaction = new Transaction(1234L, new CashCard(123L, "sarah1", 1.00));

		ResponseEntity<Void> response = testRestTemplate.postForEntity("/cashcards", testTransaction, Void.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		given(dataSourceService.getData()).willReturn(testTransaction);
		Message<byte[]> transactionReceived =  outputDestination.receive(5000, "approvalRequest-out-0");
		ObjectMapper objectMapper = new ObjectMapper();
		Transaction transaction = objectMapper.readValue(transactionReceived.getPayload(), Transaction.class);
		assertThat(transaction.id()).isEqualTo(1234L);
		assertThat(transaction.cashCard()).isEqualTo(testTransaction.cashCard());
	}
}
