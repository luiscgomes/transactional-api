package com.transactions.transactionalapi.domain.entities;

import com.transactions.transactionalapi.domain.enums.OperationTypes;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.tomcat.jni.Local;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Transaction {
    @Getter
    private UUID id;

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

        if (isValidPositiveAmount(operationType, amount) == false)
            throw new IllegalArgumentException(operationType.name() + " must be a positive amount");
        else if (isValidNegativeAmount(operationType, amount) == false)
            throw new IllegalArgumentException(operationType.name() + " must be a negative amount");

        this.id = UUID.randomUUID();
        this.eventDate = LocalDateTime.now();
        this.account = account;
        this.operationType = operationType;
        this.amount = amount;
    }

    public Transaction(UUID id, LocalDateTime eventDate, Account account, OperationTypes operationType, BigDecimal amount) {
        this(account, operationType, amount);

        this.id = id;
        this.eventDate = eventDate;
    }

    public static boolean isValidPositiveAmount(OperationTypes operationType, BigDecimal amount) {
        if (operationsWithPositiveAmount().contains(operationType) == false)
            return true;

        return amount.compareTo(BigDecimal.ZERO) > 0;
    }

    public static boolean isValidNegativeAmount(OperationTypes operationType, BigDecimal amount) {
        if (operationsWithNegativeAmount().contains(operationType) == false)
            return true;

        return amount.compareTo(BigDecimal.ZERO) < 0;
    }

    public static List<OperationTypes> operationsWithPositiveAmount() {
        return new ArrayList<>() {{
            add(OperationTypes.Payment);
        }};
    }

    public static List<OperationTypes> operationsWithNegativeAmount() {
        return new ArrayList<OperationTypes>() {{
            add(OperationTypes.CashPurchase);
            add(OperationTypes.InstallmentPurchase);
            add(OperationTypes.Withdrawal);
        }};
    }
}
