package com.transactions.transactionalapi.unitTests.commons;

import com.transactions.transactionalapi.commons.Cpf;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

public class CpfTest {
    @ParameterizedTest
    @ValueSource(strings = { "30716558009", "59160961007", "04543074033" })
    void validate_shouldReturnTrueWhenCpfIsValid(String cpf) {
        var actual = Cpf.validate(cpf);

        assertThat(actual).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = { "3071655800000000", " ", "123" })
    void validate_shouldReturnFalseWhenCpfIsInvalid(String cpf) {
        var actual = Cpf.validate(cpf);

        assertThat(actual).isFalse();
    }

    @ParameterizedTest
    @ValueSource(strings = { "30716558009", "59160961007", "04543074033" })
    void isCpfLength_shouldReturnTrueWhenCpfLength(String cpf) {
        var actual = Cpf.isCpfLength(cpf);

        assertThat(actual).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = { "30716558000", " ", "123" })
    void isCpfLength_shouldReturnFalseWhenCpfLength(String cpf) {
        var actual = Cpf.isCpfLength(cpf);

        assertThat(actual).isFalse();
    }
}
