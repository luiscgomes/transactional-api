package com.transactions.transactionalapi.integrationTests.repositories;

import com.transactions.transactionalapi.domain.entities.Account;
import com.transactions.transactionalapi.domain.entities.Transaction;
import com.transactions.transactionalapi.domain.enums.OperationTypes;
import com.transactions.transactionalapi.domain.repositories.AccountWriter;
import com.transactions.transactionalapi.domain.repositories.TransactionWriter;
import com.transactions.transactionalapi.infrastructure.respositories.TransactionJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class TransactionWriterIntegrationTest {
    @Autowired
    TransactionJpaRepository transactionJpaRepository;

    @Autowired
    AccountWriter accountWriter;

    @Autowired
    TransactionWriter transactionWriter;

    @Test
    void create_shouldCreateNewTransaction() {
        var account = new Account("32866128044");
        accountWriter.create(account);

        var transaction = new Transaction(account, OperationTypes.Withdrawal, BigDecimal.valueOf(-150));

        transactionWriter.create(transaction);

        var optionalCreatedTransaction = transactionJpaRepository.findById(transaction.getId());
        var createdTransaction = optionalCreatedTransaction.get();

        assertThat(optionalCreatedTransaction).isPresent();
        assertThat(createdTransaction.getId()).isEqualTo(transaction.getId());
        assertThat(createdTransaction.getAccountId()).isEqualTo(transaction.getAccount().getId());
        assertThat(createdTransaction.getAmount().compareTo(transaction.getAmount())).isZero();
        assertThat(createdTransaction.getEventDate()).isEqualTo(transaction.getEventDate());
        assertThat(createdTransaction.getOperationTypeId()).isEqualTo(transaction.getOperationType().getOperationTypeId());

    }
}
