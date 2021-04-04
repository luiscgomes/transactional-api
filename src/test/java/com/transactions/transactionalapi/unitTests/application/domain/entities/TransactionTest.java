package com.transactions.transactionalapi.unitTests.application.domain.entities;

import com.transactions.transactionalapi.domain.entities.Account;
import com.transactions.transactionalapi.domain.entities.Transaction;
import com.transactions.transactionalapi.domain.enums.OperationTypes;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TransactionTest {
    @ParameterizedTest
    @EnumSource(value = OperationTypes.class, mode = EnumSource.Mode.INCLUDE, names = {"CashPurchase", "InstallmentPurchase", "Withdrawal"})
    void constructor_WhenArgumentsAreValidWithNegativeAmount(OperationTypes operationType) {
        var account = new Account(UUID.randomUUID(), "21489246053", LocalDateTime.now());
        var transactionId = UUID.randomUUID();
        var eventDate = LocalDateTime.now();
        var negativeAmount = BigDecimal.valueOf(-150);

        var transaction = new Transaction(
                transactionId,
                eventDate,
                account,
                operationType,
                negativeAmount);

        assertThat(transactionId).isEqualTo(transaction.getId());
        assertThat(eventDate).isEqualTo(transaction.getEventDate());
        assertThat(operationType).isEqualTo(transaction.getOperationType());
        assertThat(account).isEqualTo(transaction.getAccount());
        assertThat(negativeAmount.compareTo(transaction.getAmount())).isZero();
    }

    @ParameterizedTest
    @EnumSource(value = OperationTypes.class, mode = EnumSource.Mode.INCLUDE, names = {"Payment"})
    void constructor_WhenArgumentsAreValidWithPositiveAmount(OperationTypes operationType) {
        var account = new Account(UUID.randomUUID(), "21489246053", LocalDateTime.now());
        var transactionId = UUID.randomUUID();
        var eventDate = LocalDateTime.now();
        var positiveAmount = BigDecimal.valueOf(130);

        var transaction = new Transaction(
                transactionId,
                eventDate,
                account,
                operationType,
                positiveAmount);

        assertThat(transactionId).isEqualTo(transaction.getId());
        assertThat(eventDate).isEqualTo(transaction.getEventDate());
        assertThat(operationType).isEqualTo(transaction.getOperationType());
        assertThat(account).isEqualTo(transaction.getAccount());
        assertThat(positiveAmount.compareTo(transaction.getAmount())).isZero();
    }

    @ParameterizedTest
    @EnumSource(value = OperationTypes.class, mode = EnumSource.Mode.INCLUDE, names = {"CashPurchase", "InstallmentPurchase", "Withdrawal"})
    void constructor_ShouldThrowExceptionWhenPositiveAmountProvidedWithNegativeOperation(OperationTypes operationType) {
        var account = new Account(UUID.randomUUID(), "21489246053", LocalDateTime.now());
        var transactionId = UUID.randomUUID();
        var eventDate = LocalDateTime.now();
        var positiveAmount = BigDecimal.valueOf(150);

        assertThatThrownBy(() -> new Transaction(
                transactionId,
                eventDate,
                account,
                operationType,
                positiveAmount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(operationType.name() + " must be a negative amount");
    }

    @ParameterizedTest
    @EnumSource(value = OperationTypes.class, mode = EnumSource.Mode.INCLUDE, names = {"Payment"})
    void constructor_ShouldThrowExceptionWhenNegativeAmountProvidedWithPositiveOperation(OperationTypes operationType) {
        var account = new Account(UUID.randomUUID(), "21489246053", LocalDateTime.now());
        var transactionId = UUID.randomUUID();
        var eventDate = LocalDateTime.now();
        var negativeAmount = BigDecimal.valueOf(-150);

        assertThatThrownBy(() -> new Transaction(
                transactionId,
                eventDate,
                account,
                operationType,
                negativeAmount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(operationType.name() + " must be a positive amount");
    }

    @Test
    void constructor_ShouldThrowExceptionWhenAccountIsNull() {
        assertThatThrownBy(() -> new Transaction(
                UUID.randomUUID(),
                LocalDateTime.now(),
                null,
                OperationTypes.Withdrawal,
                BigDecimal.valueOf(-130)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Account can not be null");
    }
}
