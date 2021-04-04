package com.transactions.transactionalapi.domain.valueObjects;

import com.transactions.transactionalapi.commons.Cnpj;
import com.transactions.transactionalapi.commons.Cpf;
import lombok.Getter;

public class DocumentNumber {
    @Getter
    private final String number;

    public DocumentNumber(String number) {
        if (Cpf.isCpfLength(number)) {
            if (Cpf.validate(number) == false)
                throw new IllegalArgumentException("number must be a valid CPF");
        } else if (Cnpj.validate(number) == false)
            throw new IllegalArgumentException("number must be a valid CNPJ");

        this.number = number;
    }
}
