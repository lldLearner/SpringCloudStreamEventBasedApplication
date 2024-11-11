package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.Message;

import com.example.domain.CashCard;
import com.example.domain.Transaction;
import com.example.source.demo.CashCardSpringCloudStreamApplication;
import com.example.source.services.DataSourceService;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(classes = CashCardSpringCloudStreamApplication.class)
@Import(TestChannelBinderConfiguration.class)
class CashCardSpringCloudStreamApplicationTests {

	@MockBean
	private DataSourceService dataSourceService;

	@Test
	void basicCashCardSupplier(@Autowired OutputDestination outputDestination) throws StreamReadException, DatabindException, IOException {
		Transaction testTransaction = new Transaction(1L, new CashCard(123L, "sarah1", 1.00));
		
		given(dataSourceService.getData()).willReturn(testTransaction);
		Message<byte[]> receivedTransaction = outputDestination.receive(5000, "approvalRequest-out-0");
		
		assertThat(receivedTransaction).isNotNull();
		ObjectMapper objectMapper = new ObjectMapper();
		Transaction transaction = objectMapper.readValue(receivedTransaction.getPayload(), Transaction.class);
		assertThat(transaction.id()).isEqualTo(1L);
		assertThat(transaction.cashCard()).isEqualTo(new CashCard(123L, "sarah1", 1.00));
	}

}
