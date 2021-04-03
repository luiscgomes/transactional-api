package com.transactions.transactionalapi.unitTests.api.controllers;

import com.transactions.transactionalapi.api.controllers.TransactionController;
import com.transactions.transactionalapi.application.models.*;
import com.transactions.transactionalapi.application.services.transactionCreators.TransactionCreator;
import com.transactions.transactionalapi.domain.enums.OperationTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TransactionControllerTest {
    private TransactionController sut;

    private TransactionCreator transactionCreator;

    @BeforeEach
    void setup() {
        transactionCreator = mock(TransactionCreator.class);
        sut = new TransactionController(transactionCreator);
    }

    @Test
    void sut_shouldGuardItsClause() {
        assertThatThrownBy(() -> new TransactionController(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("transactionCreator must not be null");
    }

    @Test
    void create_shouldReturnHttpStatusCreatedWithNewTransaction() {
        var accountId = UUID.randomUUID();
        var amount = BigDecimal.valueOf(340.20);
        var transaction = new CreatedTransactionModel(
                UUID.randomUUID(),
                LocalDateTime.now(),
                accountId,
                OperationTypes.CashPurchase.getOperationTypeId(),
                OperationTypes.CashPurchase.name(),
                amount);

        var createTransactionModel = new CreateTransactionModel(
                accountId,
                OperationTypes.CashPurchase.getOperationTypeId(),
                amount,
                null);

        var commandResult = new CommandResult<>(transaction);

        when(transactionCreator.create(createTransactionModel)).thenReturn(commandResult);

        var actual = sut.create(createTransactionModel, UriComponentsBuilder.newInstance());

        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        var createdTransaction = (CreatedTransactionModel) actual.getBody();
        assertThat(createdTransaction.getId()).isEqualTo(transaction.getId());
        assertThat(createdTransaction.getEventDate()).isEqualTo(transaction.getEventDate());
        assertThat(createdTransaction.getAccountId()).isEqualTo(transaction.getAccountId());
        assertThat(createdTransaction.getOperationTypeId()).isEqualTo(transaction.getOperationTypeId());
        assertThat(createdTransaction.getOperationTypeDescription()).isEqualTo(transaction.getOperationTypeDescription());
        assertThat(createdTransaction.getAmount().compareTo(transaction.getAmount())).isZero();
    }

    @Test
    void create_shouldReturnHttpStatusUnprocessableEntityWhenThereAreBusinessError() {
        var createTransactionModel = new CreateTransactionModel(
                UUID.randomUUID(),
                OperationTypes.Payment.getOperationTypeId(),
                BigDecimal.valueOf(140.87),
                null);

        var commandResult = new CommandResult<CreatedTransactionModel>();
        commandResult.addError("An business error has occurred");

        when(transactionCreator.create(createTransactionModel)).thenReturn(commandResult);

        var actual = sut.create(createTransactionModel, UriComponentsBuilder.newInstance());

        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);

        var errors = (ArrayList<String>)actual.getBody();
        assertThat(errors).contains("An business error has occurred");
    }
}
