package com.transactions.transactionalapi.domain.services;

import com.transactions.transactionalapi.domain.ValidationResult;
import com.transactions.transactionalapi.domain.enums.OperationTypes;

import java.math.BigDecimal;

public interface TransactionOperationAmountValidator {
    ValidationResult Validate(BigDecimal amount, OperationTypes operationType);
}
