package com.example.source.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.domain.Transaction;
import com.example.source.demo.CashCardOnDemandStream;

@RestController
@RequestMapping("/cashcards")

public class CashCardController {

	@Autowired
	private CashCardOnDemandStream cashCardOnDemandStream;


	@PostMapping
	public void publishTxn(@RequestBody Transaction transaction) {

		cashCardOnDemandStream.onDemandPublish(transaction);
		System.out.println("POST for transaction " + transaction);

	}
}
