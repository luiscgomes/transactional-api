package com.transactions.transactionalapi.domain.enums;

public enum OperationTypes {
    CashPurchase(1),
    InstallmentPurchase(2),
    Withdrawal(3),
    Payment(4);

    private int operationType;

    OperationTypes(int operationType) {
        this.operationType = operationType;
    }

    public int getOperationType() {
        return this.operationType;
    }
}
