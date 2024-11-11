package com.example.processor.services;

import java.util.UUID;

import com.example.domain.ApprovalStatus;
import com.example.domain.CardHolderData;
import com.example.domain.EnrichedTransaction;
import com.example.domain.Transaction;

public class EnrichmentService {

	public EnrichedTransaction enrichedTransaction(Transaction transaction) {

		return new EnrichedTransaction(transaction.id(), transaction.cashCard(), ApprovalStatus.Approved,
				new CardHolderData(UUID.randomUUID(), transaction.cashCard().ownerName(), "world street omaxe"));
	}
}
