package com.transactions.transactionalapi.domain.entities;

import com.transactions.transactionalapi.domain.valueObjects.DocumentNumber;
import lombok.Getter;
import java.time.LocalDateTime;
import java.util.UUID;

public class Account {
    @Getter
    private UUID id;

    @Getter
    private DocumentNumber documentNumber;

    @Getter
    private LocalDateTime createdAt;

    public Account(String documentNumber) {
        if (documentNumber == null || documentNumber.isBlank())
            throw new IllegalArgumentException("documentNumber must not be null or empty");

        this.id = UUID.randomUUID();
        this.documentNumber = new DocumentNumber(documentNumber);
        this.createdAt = LocalDateTime.now();
    }

    public Account(UUID id, String documentNumber, LocalDateTime createdAt) {
        this(documentNumber);

        this.id = id;
        this.createdAt = createdAt;
    }
}
