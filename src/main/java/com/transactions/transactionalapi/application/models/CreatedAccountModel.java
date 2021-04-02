package com.transactions.transactionalapi.application.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
public class CreatedAccountModel {
    @JsonProperty("account_id")
    @Getter private UUID id;

    @JsonProperty("created_at")
    @Getter private LocalDateTime createdAt;

    @JsonProperty("document_number")
    @Getter private String documentNumber;
}
