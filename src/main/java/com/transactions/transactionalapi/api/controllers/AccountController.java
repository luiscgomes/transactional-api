package com.transactions.transactionalapi.api.controllers;

import com.transactions.transactionalapi.application.models.CreateAccountModel;
import com.transactions.transactionalapi.application.models.CreatedAccountModel;
import com.transactions.transactionalapi.application.services.AccountCreator;
import com.transactions.transactionalapi.domain.repositories.AccountReader;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    private AccountCreator accountCreator;
    private AccountReader accountReader;

    public AccountController(AccountCreator accountCreator, AccountReader accountReader) {
        if (accountCreator == null)
            throw new IllegalArgumentException("accountCreator must not be null");

        if (accountReader == null)
            throw new IllegalArgumentException("accountReader must not be null");

        this.accountCreator = accountCreator;
        this.accountReader = accountReader;
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<?> oneById(@PathVariable UUID accountId) {
        var account = accountReader
                .one(accountId)
                .map(acc -> new CreatedAccountModel(acc.getId(), acc.getCreatedAt(), acc.getDocumentNumber()));

        if (account.isPresent() == false)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(account);
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
