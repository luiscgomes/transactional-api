package com.transactions.transactionalapi.unitTests.application.accountCreators;

import com.transactions.transactionalapi.application.models.CreateAccountModel;
import com.transactions.transactionalapi.application.services.accountCreators.AccountCreator;
import com.transactions.transactionalapi.application.services.accountCreators.AccountCreatorImplWithDocumentNumberAlreadyExits;
import com.transactions.transactionalapi.domain.entities.Account;
import com.transactions.transactionalapi.domain.repositories.AccountReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@SpringBootTest
public class AccountCreatorImplWithDocumentNumberAlreadyExitsTest {
    private AccountCreatorImplWithDocumentNumberAlreadyExits sut;

    private AccountCreator accountCreator;
    private AccountReader accountReader;

    @BeforeEach
    void setup() {
        accountCreator = mock(AccountCreator.class);
        accountReader = mock(AccountReader.class);

        sut = new AccountCreatorImplWithDocumentNumberAlreadyExits(accountCreator, accountReader);
    }

    @Test
    void sut_shouldGuardItsClause() {
        assertThatThrownBy(() -> new AccountCreatorImplWithDocumentNumberAlreadyExits(null, accountReader))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("accountCreator must not be null");

        assertThatThrownBy(() -> new AccountCreatorImplWithDocumentNumberAlreadyExits(accountCreator, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("accountReader must not be null");
    }

    @Test
    void create_shouldReturnResultWithErrorWhenThereIsAccountAlreadyWithDocumentNumber() {
        var documentNumber = "21489246053";

        var account = new Account(UUID.randomUUID(), documentNumber, LocalDateTime.now());
        var createAccountModel = new CreateAccountModel(documentNumber);

        when(accountReader.oneByDocumentNumber(documentNumber)).thenReturn(Optional.of(account));

        var actual = sut.create(createAccountModel);

        verify(accountCreator, never()).create(createAccountModel);

        assertThat(actual.hasError()).isTrue();
        assertThat(actual.getErrors()).contains("Document number must be unique. There is already an account with provided document number");
    }
}
