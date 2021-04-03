package com.transactions.transactionalapi.application.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.transactions.transactionalapi.application.modelValidators.DocumentNumberConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
public class CreateAccountModel {
    @Getter
    @Setter
    @JsonProperty("document_number")
    @NotBlank(message = "Document number is required")
    @Size(min = 11, max = 14, message = "Document number min length is 11 and max length is 14")
    @DocumentNumberConstraint
    private String documentNumber;
}
