package com.transactions.transactionalapi.api.controllers;

import com.transactions.transactionalapi.application.models.CreateAccountModel;
import com.transactions.transactionalapi.application.models.CreateTransactionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CreateTransactionModel transactionModel, UriComponentsBuilder uriBuilder) {
        var uri = uriBuilder
                .path("/transactions/{transactionId}")
                .buildAndExpand(1)
                .toUri();

        return ResponseEntity.created(uri).body(transactionModel);
    }
}
