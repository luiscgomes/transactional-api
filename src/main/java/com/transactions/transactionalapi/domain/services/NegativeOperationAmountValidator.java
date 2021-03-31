package com.transactions.transactionalapi.domain.services;

import com.transactions.transactionalapi.domain.ValidationResult;
import com.transactions.transactionalapi.domain.entities.Transaction;
import com.transactions.transactionalapi.domain.enums.OperationTypes;

import java.math.BigDecimal;

public class NegativeOperationAmountValidator implements TransactionOperationAmountValidator{
    @Override
    public ValidationResult Validate(BigDecimal amount, OperationTypes operationType) {
        if (Transaction.isValidNegativeAmount(operationType, amount) == false)
            return new ValidationResult(
                    false,
                    String.format("Amount must be negative to %s operation", operationType.name()));

        return new ValidationResult(true, null);
    }
}
