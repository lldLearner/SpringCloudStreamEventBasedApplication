package com.example.source.services;

import java.util.Random;

import com.example.domain.CashCard;
import com.example.domain.Transaction;

public class DataSourceService {

	public Transaction getData() {
		CashCard cashCard = new CashCard(new Random().nextLong(), "sarah1", new Random().nextDouble(100.00));
		return new Transaction(new Random().nextLong(), cashCard);
	}

}
