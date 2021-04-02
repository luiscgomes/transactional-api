package com.transactions.transactionalapi.application.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class CreateAccountModel {
    @Getter
    @Setter
    @JsonProperty("document_number")
    private String documentNumber;
}
