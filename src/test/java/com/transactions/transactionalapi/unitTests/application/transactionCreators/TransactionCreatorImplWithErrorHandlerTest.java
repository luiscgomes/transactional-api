package com.transactions.transactionalapi.unitTests.application.transactionCreators;

import com.transactions.transactionalapi.application.models.CommandResult;
import com.transactions.transactionalapi.application.models.CreateTransactionModel;
import com.transactions.transactionalapi.application.models.CreatedTransactionModel;
import com.transactions.transactionalapi.application.services.transactionCreators.TransactionCreator;
import com.transactions.transactionalapi.application.services.transactionCreators.TransactionCreatorImplWithErrorHandler;
import com.transactions.transactionalapi.domain.enums.OperationTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@SpringBootTest
public class TransactionCreatorImplWithErrorHandlerTest {
    private TransactionCreatorImplWithErrorHandler sut;

    private TransactionCreator transactionCreator;
    private Logger logger;

    @BeforeEach
    void setup() {
        transactionCreator = mock(TransactionCreator.class);
        logger = mock(Logger.class);

        sut = new TransactionCreatorImplWithErrorHandler(transactionCreator, logger);
    }

    @Test
    void sut_shouldGuardItsClause() {
        assertThatThrownBy(() -> new TransactionCreatorImplWithErrorHandler(null, logger))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("transactionCreator must not be null");

        assertThatThrownBy(() -> new TransactionCreatorImplWithErrorHandler(transactionCreator, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("logger must not be null");
    }

    @Test
    void create_ShouldCallInnerTransactionCreatorWhenThereIsNoError() {
        var accountId = UUID.randomUUID();
        var amount = BigDecimal.valueOf(100);
        var operationType = OperationTypes.InstallmentPurchase;

        var createTransactionModel = new CreateTransactionModel(
                accountId,
                operationType.getOperationTypeId(),
                amount,
                null);

        var createdTransaction = new CreatedTransactionModel(
                UUID.randomUUID(),
                LocalDateTime.now(),
                accountId,
                operationType.getOperationTypeId(),
                operationType.name(),
                amount
        );

        var commandResult = new CommandResult<>(createdTransaction);

        when(transactionCreator.create(createTransactionModel)).thenReturn(commandResult);

        var actual = sut.create(createTransactionModel);

        verify(transactionCreator).create(createTransactionModel);
        verify(logger, never()).error(any());

        assertThat(commandResult).isEqualTo(actual);
    }

    @Test
    void create_ShouldLogErrorWhenInnerTransactionCreatorThrowsException() {
        var createTransactionModel = new CreateTransactionModel(
                UUID.randomUUID(),
                OperationTypes.CashPurchase.getOperationTypeId(),
                BigDecimal.valueOf(130),
                null);

        var ex = new IllegalArgumentException();
        when(transactionCreator.create(createTransactionModel)).thenThrow(ex);

        assertThatThrownBy(() -> sut.create(createTransactionModel));

        verify(transactionCreator).create(createTransactionModel);
        verify(logger).error(eq("An error has occurred while creating transaction"), any(IllegalArgumentException.class));
    }
}
