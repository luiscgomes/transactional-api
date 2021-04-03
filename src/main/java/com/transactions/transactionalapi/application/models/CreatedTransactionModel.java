package com.transactions.transactionalapi.application.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
public class CreatedTransactionModel {
    @Getter
    @JsonProperty("transaction_id")
    private final UUID id;

    @Getter
    @JsonProperty("event_date")
    private final LocalDateTime eventDate;

    @Getter
    @JsonProperty("account_id")
    private final UUID accountId;

    @Getter
    @JsonProperty("operation_type_id")
    private final Integer operationTypeId;

    @Getter
    @JsonProperty("operation_type_description")
    private final String operationTypeDescription;

    @Getter
    @JsonProperty("amount")
    private final BigDecimal amount;
}
