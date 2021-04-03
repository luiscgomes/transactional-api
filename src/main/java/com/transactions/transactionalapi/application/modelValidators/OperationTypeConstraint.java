package com.transactions.transactionalapi.application.modelValidators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = OperationTypeValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface OperationTypeConstraint {
    String message() default "Operation type id provided is invalid";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
