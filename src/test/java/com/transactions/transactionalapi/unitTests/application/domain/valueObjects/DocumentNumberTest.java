package com.transactions.transactionalapi.unitTests.application.domain.valueObjects;

import com.transactions.transactionalapi.domain.entities.Account;
import com.transactions.transactionalapi.domain.valueObjects.DocumentNumber;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DocumentNumberTest {
    @ParameterizedTest
    @ValueSource(strings = { "04250497011", "08113195041", "75427038000144", "75427038000144" })
    void constructor_WhenDocumentNumbersAreValidCpfOrCnpj(String number) {
        var documentNumber = new DocumentNumber(number);

        assertThat(number).isEqualTo(documentNumber.getNumber());
    }

    @ParameterizedTest
    @ValueSource(strings = { "11111111111", "22222222222", "48817533040" })
    void constructor_WhenDocumentNumbersAreInvalidCpf(String number) {
        assertThatThrownBy(() -> new DocumentNumber(number))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("number must be a valid CPF");
    }

    @ParameterizedTest
    @ValueSource(strings = { "11111111111111", "123333", " ", "45945003000138" })
    void constructor_WhenDocumentNumbersAreInvalidCnpj(String number) {
        assertThatThrownBy(() -> new DocumentNumber(number))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("number must be a valid CNPJ");
    }
}
