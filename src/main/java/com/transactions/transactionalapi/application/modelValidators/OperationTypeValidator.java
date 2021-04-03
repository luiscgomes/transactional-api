package com.transactions.transactionalapi.application.modelValidators;

import com.transactions.transactionalapi.domain.enums.OperationTypes;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class OperationTypeValidator implements ConstraintValidator<OperationTypeConstraint, Integer> {
    @Override
    public void initialize(OperationTypeConstraint operationType) {
    }

    @Override
    public boolean isValid(Integer operationType, ConstraintValidatorContext constraintValidatorContext) {
        return Arrays
                .stream(OperationTypes.values())
                .anyMatch(ot -> ot.getOperationTypeId() == operationType);
    }
}
