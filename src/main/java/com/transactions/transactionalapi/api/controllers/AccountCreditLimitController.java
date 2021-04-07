package com.transactions.transactionalapi.api.controllers;

import com.transactions.transactionalapi.application.models.CreateCreditLimitModel;
import com.transactions.transactionalapi.application.services.creditLimitCreators.CreditLimitCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/accounts")
public class AccountCreditLimitController {
    @Autowired
    CreditLimitCreator creditLimitCreator;

    @PostMapping("/{accountId}/credit-limits")
    public ResponseEntity<?> create(@Valid @RequestBody CreateCreditLimitModel creditLimitModel, @PathVariable UUID accountId, UriComponentsBuilder uriBuilder) {
        var createdLimit = creditLimitCreator.create(creditLimitModel, accountId);
        var uri = uriBuilder
                .path("")
                .buildAndExpand()
                .toUri();

        return ResponseEntity.created(uri).body(createdLimit);
    }
 }
