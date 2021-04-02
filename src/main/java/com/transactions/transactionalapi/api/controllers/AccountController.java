package com.transactions.transactionalapi.api.controllers;

import com.transactions.transactionalapi.application.models.CreateAccountModel;
import com.transactions.transactionalapi.application.services.AccountCreator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    private AccountCreator accountCreator;

    public AccountController(AccountCreator accountCreator) {
        if (accountCreator == null)
            throw new IllegalArgumentException("accountCreator must not be null");

        this.accountCreator = accountCreator;
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<?> oneById(@PathVariable Long accountId) {
        return ResponseEntity.ok(new CreateAccountModel("ola"));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CreateAccountModel createAccountModel, UriComponentsBuilder uriBuilder) {
        var createdAccount = accountCreator.create(createAccountModel).get();

        var uri = uriBuilder
                .path("/accounts/{accountId}")
                .buildAndExpand(createdAccount.getId())
                .toUri();

        return ResponseEntity.created(uri).body(createdAccount);
    }
}
