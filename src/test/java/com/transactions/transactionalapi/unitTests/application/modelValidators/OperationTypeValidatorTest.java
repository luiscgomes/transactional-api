package com.transactions.transactionalapi.unitTests.application.modelValidators;

import com.transactions.transactionalapi.application.modelValidators.DocumentNumberValidator;
import com.transactions.transactionalapi.application.modelValidators.OperationTypeValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import javax.validation.ConstraintValidatorContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class OperationTypeValidatorTest {
    private OperationTypeValidator sut;
    private ConstraintValidatorContext constraintValidatorContext;

    @BeforeEach
    void setup() {
        sut = new OperationTypeValidator();
        constraintValidatorContext = mock(ConstraintValidatorContext.class);
    }

    @ParameterizedTest
    @ValueSource(ints = { 1, 2, 3, 4 })
    void isValid_ShouldReturnTrueWhenValidOperationTypeId(Integer operationTypeId) {
        var actual = sut.isValid(operationTypeId, constraintValidatorContext);

        assertThat(actual).isTrue();
    }

    @ParameterizedTest
    @ValueSource(ints = { -1, 5, 6, 0 })
    void isValid_ShouldReturnFalseWhenInvalidOperationTypeId(Integer operationTypeId) {
        var actual = sut.isValid(operationTypeId, constraintValidatorContext);

        assertThat(actual).isFalse();
    }
}
