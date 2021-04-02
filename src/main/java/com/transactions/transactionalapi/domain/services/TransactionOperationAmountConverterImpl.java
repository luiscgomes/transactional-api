package com.transactions.transactionalapi.domain.services;

import com.transactions.transactionalapi.domain.entities.Transaction;
import com.transactions.transactionalapi.domain.enums.OperationTypes;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class TransactionOperationAmountConverterImpl implements TransactionOperationAmountConverter {
    @Override
    public BigDecimal Convert(BigDecimal amount, OperationTypes operationType) {
        if (Transaction.operationsWithNegativeAmount().contains(operationType))
            return amount.negate();

        return amount;
    }
}
