package com.transactions.transactionalapi.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
public class Account {
    @Getter
    private UUID id;

    @Getter
    private String documentNumber;

    @Getter
    private LocalDateTime createdAt;

    public Account(String documentNumber) {
        if (documentNumber == null || documentNumber.isEmpty())
            throw new IllegalArgumentException("documentNumber must not be null or empty");

        this.id = UUID.randomUUID();
        this.documentNumber = documentNumber;
        this.createdAt = LocalDateTime.now();
    }
}
