package com.transactions.transactionalapi.domain.services;

import com.transactions.transactionalapi.domain.enums.OperationTypes;

import java.math.BigDecimal;

public interface TransactionOperationAmountConverter {
    BigDecimal convert(BigDecimal amount, OperationTypes operationType);
}
