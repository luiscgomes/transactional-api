package com.transactions.transactionalapi.application.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
public class CreatedCreditLimitModel {
    @Getter
    private UUID id;

    @Getter
    private LocalDateTime createdAt;

    @Getter
    private UUID accountId;

    @Getter
    private BigDecimal limit;
}
