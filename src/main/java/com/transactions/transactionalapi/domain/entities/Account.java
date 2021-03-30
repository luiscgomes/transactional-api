package com.transactions.transactionalapi.domain.entities;

import lombok.Getter;

public class Account {
    @Getter
    private Long id;

    @Getter
    private String documentNumber;
}
