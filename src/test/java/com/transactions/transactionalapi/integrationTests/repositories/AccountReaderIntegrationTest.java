package com.transactions.transactionalapi.integrationTests.repositories;

import com.transactions.transactionalapi.domain.repositories.AccountReader;
import com.transactions.transactionalapi.infrastructure.dtos.AccountDto;
import com.transactions.transactionalapi.infrastructure.respositories.AccountJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AccountReaderIntegrationTest {
    @Autowired
    AccountJpaRepository accountJpaRepository;

    @Autowired
    AccountReader accountReader;

    @Test
    void one_shouldReturnAccountWhenItExists() {
        var accountId = UUID.randomUUID();
        var accountDto = new AccountDto(accountId, LocalDateTime.now(), "57920370000164");

        accountJpaRepository.save(accountDto);

        var optionalAccount = accountReader.one(accountId);

        var account = optionalAccount.get();

        assertThat(optionalAccount).isPresent();
        assertThat(account.getId()).isEqualTo(accountId);
        assertThat(account.getDocumentNumber().getNumber()).isEqualTo(accountDto.getDocumentNumber());
        assertThat(account.getCreatedAt()).isEqualTo(accountDto.getCreatedAt());
    }

    @Test
    void one_shouldReturnEmptyWhenAccountDoesNotExist() {
        var accountDto = new AccountDto(UUID.randomUUID(), LocalDateTime.now(), "00206405000180");

        accountJpaRepository.save(accountDto);

        var account = accountReader.one(UUID.randomUUID());

        assertThat(account).isEmpty();
    }

    @Test
    void oneByDocumentNumber_shouldReturnAccountWhenItExists() {
        var documentNumber = "36105298000141";
        var accountDto = new AccountDto(UUID.randomUUID(), LocalDateTime.now(), documentNumber);

        accountJpaRepository.save(accountDto);

        var optionalAccount = accountReader.oneByDocumentNumber(documentNumber);

        var account = optionalAccount.get();

        assertThat(optionalAccount).isPresent();
        assertThat(account.getId()).isEqualTo(accountDto.getId());
        assertThat(account.getDocumentNumber().getNumber()).isEqualTo(documentNumber);
        assertThat(account.getCreatedAt()).isEqualTo(accountDto.getCreatedAt());
    }

    @Test
    void oneByDocumentNumber_shouldReturnEmptyWhenAccountDoesNotExist() {
        var accountDto = new AccountDto(UUID.randomUUID(), LocalDateTime.now(), "51921214000131");

        accountJpaRepository.save(accountDto);

        var account = accountReader.oneByDocumentNumber("70821317000174");

        assertThat(account).isEmpty();
    }
}
