package com.transactions.transactionalapi.api.controllers;

import com.transactions.transactionalapi.application.models.CreateAccountModel;
import com.transactions.transactionalapi.application.models.CreatedAccountModel;
import com.transactions.transactionalapi.application.services.accountCreators.AccountCreator;
import com.transactions.transactionalapi.domain.repositories.AccountReader;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    private final AccountCreator accountCreator;

    private final AccountReader accountReader;

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
                .map(acc -> new CreatedAccountModel(acc.getId(), acc.getCreatedAt(), acc.getDocumentNumber().getNumber()));

        if (account.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(account.get());
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CreateAccountModel createAccountModel, UriComponentsBuilder uriBuilder) {
        var result = accountCreator.create(createAccountModel);

        if (result.hasError())
            return ResponseEntity.unprocessableEntity().body(result.getErrors());

        var createdAccount = result.getValue().get();

        var uri = uriBuilder
                .path("/accounts/{accountId}")
                .buildAndExpand(createdAccount.getId())
                .toUri();

        return ResponseEntity.created(uri).body(createdAccount);
    }
}
