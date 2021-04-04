package com.transactions.transactionalapi.unitTests.domain.entities;

import com.transactions.transactionalapi.domain.entities.Account;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AccountTest {
    @Test
    void constructor_WhenArgumentsAreValid() {
        var accountId = UUID.randomUUID();
        var createdAt = LocalDateTime.now();
        var documentNumber = "21489246053";

        var account = new Account(accountId, documentNumber, createdAt);

        assertThat(accountId).isEqualTo(account.getId());
        assertThat(createdAt).isEqualTo(account.getCreatedAt());
        assertThat(documentNumber).isEqualTo(account.getDocumentNumber().getNumber());
    }

    @ParameterizedTest
    @ValueSource(strings = { " ", "" })
    void constructor_WhenDocumentNumberIsBlank(String documentNumber) {
        var accountId = UUID.randomUUID();
        var createdAt = LocalDateTime.now();

        assertThatThrownBy(() -> new Account(accountId, documentNumber, createdAt))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("documentNumber must not be null or empty");
    }

    @Test
    void constructor_WhenDocumentNumberIsNull() {
        var accountId = UUID.randomUUID();
        var createdAt = LocalDateTime.now();

        assertThatThrownBy(() -> new Account(accountId, null, createdAt))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("documentNumber must not be null or empty");
    }
}
