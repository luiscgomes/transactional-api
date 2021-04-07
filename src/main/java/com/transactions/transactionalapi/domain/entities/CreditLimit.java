package com.transactions.transactionalapi.domain.entities;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class CreditLimit {
    @Getter
    private UUID id;

    @Getter
    private LocalDateTime createdAt;

    @Getter
    private Account account;

    @Getter
    private BigDecimal limit;

    public CreditLimit(UUID id, LocalDateTime createdAt, Account account, BigDecimal limit) {
        this.id = id;
        this.createdAt = createdAt;
        if (account == null)
            throw new IllegalArgumentException("account must not be null");

        if (limit.compareTo(BigDecimal.ZERO) < 0)
            throw new IllegalArgumentException("limit must be greater than 0");

        this.account = account;
        this.limit = limit;
    }

    public CreditLimit(Account account, BigDecimal limit) {
        this.id = UUID.randomUUID();
        this.createdAt = LocalDateTime.now();
        this.account = account;
        this.limit = limit;
    }

    public void addLimit(BigDecimal limit) {
        this.limit = this.limit.add(limit);
    }

    public void subtractLimit(BigDecimal limit) {
        this.limit = this.limit.subtract(limit);
    }
}
