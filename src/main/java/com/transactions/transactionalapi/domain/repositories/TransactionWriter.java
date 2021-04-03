package com.transactions.transactionalapi.domain.repositories;

import com.transactions.transactionalapi.domain.entities.Transaction;

public interface TransactionWriter {
    Transaction create(Transaction transaction);
}
