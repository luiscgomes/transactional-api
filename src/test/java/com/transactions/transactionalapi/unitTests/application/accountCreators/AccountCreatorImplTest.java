package com.transactions.transactionalapi.unitTests.application.accountCreators;

import com.transactions.transactionalapi.application.models.CreateAccountModel;
import com.transactions.transactionalapi.application.services.accountCreators.AccountCreatorImpl;
import com.transactions.transactionalapi.domain.entities.Account;
import com.transactions.transactionalapi.domain.repositories.AccountWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
public class AccountCreatorImplTest {
    private AccountCreatorImpl sut;

    private AccountWriter accountWriter;

    @Captor
    ArgumentCaptor<Account> accountCaptor;

    @BeforeEach
    void setup() {
        accountWriter = mock(AccountWriter.class);

        sut = new AccountCreatorImpl(accountWriter);
    }

    @Test
    void create_shouldPersistNewAccount() {
        var documentNumber = "21489246053";

        var account = new Account(UUID.randomUUID(), documentNumber, LocalDateTime.now());
        var createAccountModel = new CreateAccountModel(documentNumber);

        when(accountWriter.create(any(Account.class))).thenReturn(account);

        var actual = sut.create(createAccountModel);

        assertThat(actual.getValue()).isPresent();

        verify(accountWriter).create(accountCaptor.capture());
        assertThat(documentNumber).isEqualTo(accountCaptor.getValue().getDocumentNumber().getNumber());

        var createdAccount = actual.getValue().get();
        assertThat(account.getId()).isEqualTo(createdAccount.getId());
        assertThat(account.getCreatedAt()).isEqualTo(createdAccount.getCreatedAt());
        assertThat(account.getDocumentNumber().getNumber()).isEqualTo(createdAccount.getDocumentNumber());
    }
}
