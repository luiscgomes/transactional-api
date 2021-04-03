package com.transactions.transactionalapi.integrationTests.repositories;

import com.transactions.transactionalapi.domain.entities.Account;
import com.transactions.transactionalapi.domain.repositories.AccountWriter;
import com.transactions.transactionalapi.infrastructure.respositories.AccountJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class AccountWriterIntegrationTest {
    @Autowired
    AccountJpaRepository accountJpaRepository;

    @Autowired
    AccountWriter accountWriter;

    @Test
    void create_shouldCreateNewTransaction() {
        var account = new Account("32866128044");

        accountWriter.create(account);

        var optionalCreatedAccount = accountJpaRepository.findById(account.getId());
        var createdAccount = optionalCreatedAccount.get();

        assertThat(optionalCreatedAccount).isPresent();
        assertThat(createdAccount.getId()).isEqualTo(account.getId());
        assertThat(createdAccount.getCreatedAt()).isEqualTo(account.getCreatedAt());
        assertThat(createdAccount.getDocumentNumber()).isEqualTo(account.getDocumentNumber().getNumber());
    }
}
