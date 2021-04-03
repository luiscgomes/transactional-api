package com.transactions.transactionalapi.application.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.transactions.transactionalapi.application.modelValidators.OperationTypeConstraint;
import com.transactions.transactionalapi.domain.enums.OperationTypes;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
public class CreateTransactionModel {
    @Getter
    @Setter
    @JsonProperty("account_id")
    @NotBlank(message = "Account id is required")
    private String accountId;

    @Getter
    @Setter
    @JsonProperty("operation_type_id")
    @NotNull(message = "Operation Type Id is required")
    @OperationTypeConstraint
    private Integer operationTypeId;

    @Getter
    @Setter
    @JsonProperty("amount")
    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be a positive value")
    private BigDecimal amount;
}
