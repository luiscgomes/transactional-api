package com.transactions.transactionalapi.unitTests.application.transactionCreators;

import com.transactions.transactionalapi.api.controllers.AccountController;
import com.transactions.transactionalapi.application.models.CreateTransactionModel;
import com.transactions.transactionalapi.application.models.CreatedTransactionModel;
import com.transactions.transactionalapi.application.services.transactionCreators.TransactionCreator;
import com.transactions.transactionalapi.application.services.transactionCreators.TransactionCreatorImpl;
import com.transactions.transactionalapi.domain.entities.Account;
import com.transactions.transactionalapi.domain.entities.Transaction;
import com.transactions.transactionalapi.domain.enums.OperationTypes;
import com.transactions.transactionalapi.domain.repositories.TransactionWriter;
import com.transactions.transactionalapi.domain.services.TransactionOperationAmountConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@SpringBootTest
public class TransactionCreatorImplTest {
    private TransactionCreatorImpl sut;

    private TransactionWriter transactionWriter;

    private TransactionOperationAmountConverter transactionOperationAmountConverter;

    @Captor
    ArgumentCaptor<Transaction> transactionCaptor;

    @BeforeEach
    void setup() {
        transactionWriter = mock(TransactionWriter.class);
        transactionOperationAmountConverter = mock(TransactionOperationAmountConverter.class);

        sut = new TransactionCreatorImpl(transactionWriter, transactionOperationAmountConverter);
    }

    @Test
    void sut_shouldGuardItsClause() {
        assertThatThrownBy(() -> new TransactionCreatorImpl(null, transactionOperationAmountConverter))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("transactionWriter must not be null");

        assertThatThrownBy(() -> new TransactionCreatorImpl(transactionWriter, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("transactionOperationAmountConverter must not be null");
    }

    @Test
    void create_shouldPersistNewTransaction() {
        var accountId = UUID.randomUUID();
        var amount = BigDecimal.valueOf(150);
        var operationType = OperationTypes.CashPurchase;

        var account = new Account(accountId, "21489246053", LocalDateTime.now());

        var createTransactionModel = new CreateTransactionModel(
                accountId,
                operationType.getOperationTypeId(),
                amount,
                account);

        var operationAmount = amount.negate();

        var transaction = new Transaction(account, operationType, operationAmount);

        when(transactionWriter.create(any())).thenReturn(transaction);
        when(transactionOperationAmountConverter.convert(amount, operationType)).thenReturn(operationAmount);

        var actual = sut.create(createTransactionModel);

        assertThat(actual.getValue()).isPresent();

        verify(transactionWriter).create(transactionCaptor.capture());
        var transactionArgument = transactionCaptor.getValue();
        assertThat(accountId).isEqualTo(transactionArgument.getAccount().getId());
        assertThat(operationAmount.compareTo(transactionArgument.getAmount())).isZero();
        assertThat(operationType).isEqualTo(transactionArgument.getOperationType());

        verify(transactionOperationAmountConverter).convert(amount, operationType);

        var createdTransaction = actual.getValue().get();
        assertThat(transaction.getId()).isEqualTo(createdTransaction.getId());
        assertThat(transaction.getEventDate()).isEqualTo(createdTransaction.getEventDate());
        assertThat(accountId).isEqualTo(createdTransaction.getAccountId());
        assertThat(operationType.getOperationTypeId()).isEqualTo(createdTransaction.getOperationTypeId());
        assertThat(operationType.name()).isEqualTo(createdTransaction.getOperationTypeDescription());
        assertThat(amount.compareTo(createdTransaction.getAmount())).isZero();
    }
}
