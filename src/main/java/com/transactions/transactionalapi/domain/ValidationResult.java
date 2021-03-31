package com.transactions.transactionalapi.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class ValidationResult {
    @Getter
    private boolean isValid;

    @Getter
    private String errorMessage;
}
