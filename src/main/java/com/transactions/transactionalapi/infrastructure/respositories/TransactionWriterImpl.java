package com.transactions.transactionalapi.infrastructure.respositories;

import com.transactions.transactionalapi.domain.entities.Transaction;
import com.transactions.transactionalapi.domain.repositories.TransactionWriter;
import com.transactions.transactionalapi.infrastructure.dtos.TransactionDto;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public class TransactionWriterImpl implements TransactionWriter {
    private final TransactionJpaRepository transactionJpaRepository;

    public TransactionWriterImpl(TransactionJpaRepository transactionJpaRepository) {
        if (transactionJpaRepository == null)
            throw new IllegalArgumentException("transactionJpaRepository must not be null");

        this.transactionJpaRepository = transactionJpaRepository;
    }

    @Override
    public Transaction create(Transaction transaction) {
        var transactionDto = new TransactionDto(
                transaction.getId(),
                transaction.getEventDate(),
                transaction.getAccount().getId(),
                transaction.getOperationType().getOperationTypeId(),
                transaction.getAmount());

        var newTransaction = transactionJpaRepository.save(transactionDto);

        return new Transaction(
                newTransaction.getId(),
                newTransaction.getEventDate(),
                transaction.getAccount(),
                transaction.getOperationType(),
                newTransaction.getAmount()
        );
    }
}
