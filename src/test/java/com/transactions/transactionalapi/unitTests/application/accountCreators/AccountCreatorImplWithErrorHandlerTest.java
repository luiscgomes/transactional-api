package com.transactions.transactionalapi.unitTests.application.accountCreators;

import com.transactions.transactionalapi.application.models.*;
import com.transactions.transactionalapi.application.services.accountCreators.AccountCreator;
import com.transactions.transactionalapi.application.services.accountCreators.AccountCreatorImplWithErrorHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class AccountCreatorImplWithErrorHandlerTest {
    private AccountCreatorImplWithErrorHandler sut;

    private AccountCreator accountCreator;
    private Logger logger;

    @BeforeEach
    void setup() {
        accountCreator = mock(AccountCreator.class);
        logger = mock(Logger.class);

        sut = new AccountCreatorImplWithErrorHandler(accountCreator, logger);
    }

    @Test
    void sut_shouldGuardItsClause() {
        assertThatThrownBy(() -> new AccountCreatorImplWithErrorHandler(null, logger))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("accountCreator must not be null");

        assertThatThrownBy(() -> new AccountCreatorImplWithErrorHandler(accountCreator, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("logger must not be null");
    }

    @Test
    void create_ShouldCallInnerAccountCreatorWhenThereIsNoError() {
        var documentNumber = "21489246053";

        var createAccountModel = new CreateAccountModel(documentNumber);

        var createdAccount = new CreatedAccountModel(UUID.randomUUID(), LocalDateTime.now(), documentNumber);

        var commandResult = new CommandResult<>(createdAccount);

        when(accountCreator.create(createAccountModel)).thenReturn(commandResult);

        var actual = sut.create(createAccountModel);

        verify(accountCreator).create(createAccountModel);
        verify(logger, never()).error(any());

        assertThat(commandResult).isEqualTo(actual);
    }

    @Test
    void create_ShouldLogErrorWhenInnerAccountCreatorThrowsException() {
        var createAccountModel = new CreateAccountModel("21489246053");

        var ex = new IllegalArgumentException();
        when(accountCreator.create(createAccountModel)).thenThrow(ex);

        assertThatThrownBy(() -> sut.create(createAccountModel));

        verify(accountCreator).create(createAccountModel);
        verify(logger).error(eq("An error has occurred while creating account"), any(IllegalArgumentException.class));
    }
}
