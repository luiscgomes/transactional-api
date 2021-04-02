package com.transactions.transactionalapi.application.modelValidators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DocumentNumberValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface DocumentNumberConstraint {
    String message() default "Document number must be a valid CPF or CNPJ";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
