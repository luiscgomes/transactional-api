package com.transactions.transactionalapi.application.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
public class CreateCreditLimitModel {
    @Getter
    @Setter
    @JsonProperty("limit")
    private BigDecimal limit;
}
