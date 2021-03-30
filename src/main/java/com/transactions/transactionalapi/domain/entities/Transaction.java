package com.transactions.transactionalapi.domain.entities;

import com.transactions.transactionalapi.domain.enums.OperationTypes;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Transaction {
    @Getter
    private Long id;

    @Getter
    private Account account;

    @Getter
    private OperationTypes operationType;

    @Getter
    private BigDecimal amount;

    @Getter
    LocalDateTime eventDate;

    public Transaction(Account account, OperationTypes operationType, BigDecimal amount) {
        if (account == null)
            throw new IllegalArgumentException("Account can not be null");

        if (isValidPositiveAmount(operationType, amount))
            throw new IllegalArgumentException(operationType.name() + "must be a positive amount");

        if (isValidNegativeAmount(operationType, amount))
            throw new IllegalArgumentException(operationType.name() + "must be a negative amount");

        this.account = account;
        this.operationType = operationType;
        this.amount = amount;
    }

    public boolean isValidPositiveAmount(OperationTypes operationType, BigDecimal amount) {
        return operationsWithPositiveAmount().contains(operationType) &&
                amount.compareTo(BigDecimal.ZERO) > 0;
    }

    public boolean isValidNegativeAmount(OperationTypes operationType, BigDecimal amount) {
        return operationsWithNegativeAmount().contains(operationType) &&
                amount.compareTo(BigDecimal.ZERO) < 0;
    }

    public List<OperationTypes> operationsWithPositiveAmount() {
        return new ArrayList<>() {{
            add(OperationTypes.Payment);
        }};
    }

    public List<OperationTypes> operationsWithNegativeAmount() {
        return new ArrayList<OperationTypes>(){{
            add(OperationTypes.CashPurchase);
            add(OperationTypes.InstallmentPurchase);
            add(OperationTypes.Withdrawal);
        }};
    }
}
