package com.transactions.transactionalapi.domain.enums;

import java.util.Arrays;

public enum OperationTypes {
    CashPurchase(1),
    InstallmentPurchase(2),
    Withdrawal(3),
    Payment(4);

    private int operationType;

    OperationTypes(int operationType) {
        this.operationType = operationType;
    }

    public int getOperationTypeId() {
        return this.operationType;
    }

    public static OperationTypes getOperationTypeValue(int operationTypeId) {
        return Arrays.stream(OperationTypes.values()).filter(ot -> ot.getOperationTypeId() == operationTypeId).findFirst().get();
    }
}
