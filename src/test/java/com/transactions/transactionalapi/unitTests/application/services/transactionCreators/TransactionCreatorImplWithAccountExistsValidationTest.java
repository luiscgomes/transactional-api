package com.transactions.transactionalapi.unitTests.application.services.transactionCreators;

import com.transactions.transactionalapi.application.models.CreateTransactionModel;
import com.transactions.transactionalapi.application.services.transactionCreators.TransactionCreator;
import com.transactions.transactionalapi.application.services.transactionCreators.TransactionCreatorImplWithAccountExistsValidation;
import com.transactions.transactionalapi.domain.entities.Account;
import com.transactions.transactionalapi.domain.enums.OperationTypes;
import com.transactions.transactionalapi.domain.repositories.AccountReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@SpringBootTest
public class TransactionCreatorImplWithAccountExistsValidationTest {
    private TransactionCreatorImplWithAccountExistsValidation sut;

    private TransactionCreator transactionCreator;
    private AccountReader accountReader;

    @Captor
    private ArgumentCaptor<CreateTransactionModel> createTransactionModelCaptor;

    @BeforeEach
    void setup() {
        transactionCreator = mock(TransactionCreator.class);
        accountReader = mock(AccountReader.class);

        sut = new TransactionCreatorImplWithAccountExistsValidation(transactionCreator, accountReader);
    }

    @Test
    void sut_shouldGuardItsClause() {
        assertThatThrownBy(() -> new TransactionCreatorImplWithAccountExistsValidation(null, accountReader))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("transactionCreator must not be null");

        assertThatThrownBy(() -> new TransactionCreatorImplWithAccountExistsValidation(transactionCreator, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("accountReader must not be null");
    }

    @Test
    void create_shouldCallInnerTransactionCreatorWhenAccountExists() {
        var accountId = UUID.randomUUID();

        var account = new Account(accountId, "21489246053", LocalDateTime.now());

        var createTransactionModel = new CreateTransactionModel(
                accountId,
                OperationTypes.Payment.getOperationTypeId(),
                BigDecimal.valueOf(100),
                account);

        when(accountReader.one(accountId)).thenReturn(Optional.of(account));

        sut.create(createTransactionModel);

        verify(transactionCreator).create(createTransactionModelCaptor.capture());

        assertThat(createTransactionModel).isEqualTo(createTransactionModel);
        assertThat(account).isEqualTo(createTransactionModel.getAccount());
    }

    @Test
    void create_shouldReturnResultWithErrorWhenAccountDoesNotExits() {
        var createTransactionModel = new CreateTransactionModel(
                UUID.randomUUID(),
                OperationTypes.Payment.getOperationTypeId(),
                BigDecimal.valueOf(100),
                null);

        when(accountReader.one(createTransactionModel.getAccountId())).thenReturn(Optional.empty());

        var actual = sut.create(createTransactionModel);

        verify(transactionCreator, never()).create(createTransactionModel);

        assertThat(actual.hasError()).isTrue();
        assertThat(actual.getErrors()).contains("Account does not exit for provided account id");
    }
}
