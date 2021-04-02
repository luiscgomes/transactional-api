package com.transactions.transactionalapi.application.modelValidators;

import com.transactions.transactionalapi.commons.Cnpj;
import com.transactions.transactionalapi.commons.Cpf;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DocumentNumberValidator implements ConstraintValidator<DocumentNumberConstraint, String> {
    @Override
    public void initialize(DocumentNumberConstraint documentNumber) {
    }

    @Override
    public boolean isValid(String number, ConstraintValidatorContext constraintValidatorContext) {
        if (Cpf.isCpfLength(number))
            return Cpf.validate(number);

        return Cnpj.validate(number);
    }
}
