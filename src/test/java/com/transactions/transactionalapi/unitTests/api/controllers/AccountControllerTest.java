package com.transactions.transactionalapi.unitTests.api.controllers;

import com.transactions.transactionalapi.api.controllers.AccountController;
import com.transactions.transactionalapi.application.models.CommandResult;
import com.transactions.transactionalapi.application.models.CreateAccountModel;
import com.transactions.transactionalapi.application.models.CreatedAccountModel;
import com.transactions.transactionalapi.application.services.accountCreators.AccountCreator;
import com.transactions.transactionalapi.domain.entities.Account;
import com.transactions.transactionalapi.domain.repositories.AccountReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class AccountControllerTest {
    private AccountController sut;

    private AccountCreator accountCreator;
    private AccountReader accountReader;

    @BeforeEach
    void setup() {
        accountCreator = mock(AccountCreator.class);
        accountReader = mock(AccountReader.class);

        sut = new AccountController(accountCreator, accountReader);
    }

    @Test
    void sut_shouldGuardItsClause() {
        assertThatThrownBy(() -> new AccountController(null, accountReader))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("accountCreator must not be null");

        assertThatThrownBy(() -> new AccountController(accountCreator, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("accountReader must not be null");
    }

    @Test
    void create_shouldReturnHttpStatusCreatedWithNewAccount() {
        var documentNumber = "21489246053";
        var account = new CreatedAccountModel(UUID.randomUUID(), LocalDateTime.now(), documentNumber);
        var createAccountModel = new CreateAccountModel(documentNumber);

        var commandResult = new CommandResult<>(account);

        when(accountCreator.create(createAccountModel)).thenReturn(commandResult);

        var actual = sut.create(createAccountModel, UriComponentsBuilder.newInstance());

        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        var createdAccount = (CreatedAccountModel)actual.getBody();
        assertThat(createdAccount.getId()).isEqualTo(account.getId());
        assertThat(createdAccount.getCreatedAt()).isEqualTo(account.getCreatedAt());
        assertThat(createdAccount.getDocumentNumber()).isEqualTo(account.getDocumentNumber());
    }

    @Test
    void create_shouldReturnHttpStatusUnprocessableEntityWhenThereAreBusinessError() {
        var createAccountModel = new CreateAccountModel("21489246053");
        var commandResult = new CommandResult<CreatedAccountModel>();
        commandResult.addError("An business error has occurred");

        when(accountCreator.create(createAccountModel)).thenReturn(commandResult);

        var actual = sut.create(createAccountModel, UriComponentsBuilder.newInstance());

        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);

        var errors = (ArrayList<String>)actual.getBody();
        assertThat(errors).contains("An business error has occurred");
    }

    @Test
    void oneById_shouldReturnHttpStatusOkWithAccount() {
        var account = new Account("21489246053");

        when(accountReader.one(account.getId())).thenReturn(Optional.of(account));

        var actual = sut.oneById(account.getId());

        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);

        var createdAccount = (CreatedAccountModel)actual.getBody();
        assertThat(createdAccount.getId()).isEqualTo(account.getId());
        assertThat(createdAccount.getDocumentNumber()).isEqualTo(account.getDocumentNumber().getNumber());
        assertThat(createdAccount.getCreatedAt()).isEqualTo(account.getCreatedAt());
    }

    @Test
    void oneById_shouldReturnHttpStatusNotFoundWhenAccountDoesNotExist() {
        var accountId = UUID.randomUUID();
        when(accountReader.one(accountId)).thenReturn(Optional.empty());

        var actual = sut.oneById(accountId);

        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
