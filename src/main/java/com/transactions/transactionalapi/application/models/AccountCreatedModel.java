package com.transactions.transactionalapi.application.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
public class AccountCreatedModel {
    @Getter private Long id;
    @Getter private LocalDateTime createdAt;
    @Getter private String documentNumber;
}
