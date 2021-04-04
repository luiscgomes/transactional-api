package com.transactions.transactionalapi.unitTests.application.domain.services;

import com.transactions.transactionalapi.domain.enums.OperationTypes;
import com.transactions.transactionalapi.domain.services.TransactionOperationAmountConverterImpl;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class TransactionOperationAmountConverterImplTest {
    @ParameterizedTest
    @EnumSource(value = OperationTypes.class, mode = EnumSource.Mode.INCLUDE, names = {"CashPurchase", "InstallmentPurchase", "Withdrawal"})
    void convert_ShouldReturnNegativeAmountWhenNegativeOperation(OperationTypes operationType) {
        var amount = BigDecimal.valueOf(142);

        var sut = new TransactionOperationAmountConverterImpl();
        var actual = sut.convert(amount, operationType);

        assertThat(amount.negate()).isEqualTo(actual);
    }

    @ParameterizedTest
    @EnumSource(value = OperationTypes.class, mode = EnumSource.Mode.INCLUDE, names = {"Payment"})
    void convert_ShouldReturnPositiveAmountWhenNegativeOperation(OperationTypes operationType) {
        var amount = BigDecimal.valueOf(459);

        var sut = new TransactionOperationAmountConverterImpl();
        var actual = sut.convert(amount, operationType);

        assertThat(amount).isEqualTo(actual);
    }
}
