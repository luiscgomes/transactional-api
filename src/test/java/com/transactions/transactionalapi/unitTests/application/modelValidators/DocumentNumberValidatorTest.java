package com.transactions.transactionalapi.unitTests.application.modelValidators;

import com.transactions.transactionalapi.application.modelValidators.DocumentNumberValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import javax.validation.ConstraintValidatorContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class DocumentNumberValidatorTest {
    private DocumentNumberValidator sut;
    private ConstraintValidatorContext constraintValidatorContext;

    @BeforeEach
    void setup() {
        sut = new DocumentNumberValidator();
        constraintValidatorContext = mock(ConstraintValidatorContext.class);
    }

    @ParameterizedTest
    @ValueSource(strings = { "45945003000136", "39910814000107", "30716558009", "59160961007" })
    void isValid_ShouldReturnTrueWhenValidDocumentNumber(String documentNumber) {
        var actual = sut.isValid(documentNumber, constraintValidatorContext);

        assertThat(actual).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = { "123", "39910814000108", "30716", " " })
    void isValid_ShouldReturnFalseWhenInvalidDocumentNumber(String documentNumber) {
        var actual = sut.isValid(documentNumber, constraintValidatorContext);

        assertThat(actual).isFalse();
    }
}
