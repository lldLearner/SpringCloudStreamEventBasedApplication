package com.example.source.demo;

import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Configuration;

import com.example.domain.Transaction;

@Configuration
public class CashCardOnDemandStream {

	private StreamBridge streamBridge;

	public CashCardOnDemandStream(StreamBridge streamBridge) {
		// TODO Auto-generated constructor stub
		this.streamBridge = streamBridge;
	}

	public void onDemandPublish(Transaction transaction) {
		// TODO Auto-generated method stub
		streamBridge.send("approvalRequest-out-0", transaction);
	}
}
