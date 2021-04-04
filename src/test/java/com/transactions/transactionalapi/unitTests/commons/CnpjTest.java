package com.transactions.transactionalapi.unitTests.commons;

import com.transactions.transactionalapi.commons.Cnpj;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

public class CnpjTest {
    @ParameterizedTest
    @ValueSource(strings = { "11757746000160", "47756094000197", "28837107000108" })
    void validate_shouldReturnTrueWhenCnpjIsValid(String cnpj) {
        var actual = Cnpj.validate(cnpj);

        assertThat(actual).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = { "11757746000161", " ", "122222222222222" })
    void validate_shouldReturnFalseWhenCnpjIsInvalid(String cnpj) {
        var actual = Cnpj.validate(cnpj);

        assertThat(actual).isFalse();
    }
}
