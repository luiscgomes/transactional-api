package com.transactions.transactionalapi.api.controllers;

import com.transactions.transactionalapi.application.models.CreateTransactionModel;
import com.transactions.transactionalapi.application.models.CreatedAccountModel;
import com.transactions.transactionalapi.application.services.transactionCreators.TransactionCreator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    private final TransactionCreator transactionCreator;

    public TransactionController(TransactionCreator transactionCreator) {
        if (transactionCreator == null)
            throw new IllegalArgumentException("transactionCreator must not be null");

        this.transactionCreator = transactionCreator;
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CreateTransactionModel transactionModel, UriComponentsBuilder uriBuilder) {
        var result = transactionCreator.create(transactionModel);

        if (result.hasError())
            return ResponseEntity.unprocessableEntity().body(result.getErrors());

        var createdTransaction = result.getValue().get();

        var uri = uriBuilder
                .path("/transactions/{transactionId}")
                .buildAndExpand(createdTransaction.getId())
                .toUri();

        return ResponseEntity.created(uri).body(createdTransaction);
    }
}
