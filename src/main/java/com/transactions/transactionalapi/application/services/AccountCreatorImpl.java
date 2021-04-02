package com.transactions.transactionalapi.application.services;

import com.transactions.transactionalapi.application.models.CreatedAccountModel;
import com.transactions.transactionalapi.application.models.CreateAccountModel;
import com.transactions.transactionalapi.domain.entities.Account;
import com.transactions.transactionalapi.domain.repositories.AccountWriter;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountCreatorImpl implements AccountCreator {
    private AccountWriter accountWriter;

    public AccountCreatorImpl(AccountWriter accountWriter) {
        if (accountWriter == null)
            throw new IllegalArgumentException("accountWriter must not be null");

        this.accountWriter = accountWriter;
    }

    @Override
    public Optional<CreatedAccountModel> create(CreateAccountModel accountModel) {
        var account = new Account(accountModel.getDocumentNumber());

        var newAccount = accountWriter.create(account);

        return Optional.of(new CreatedAccountModel(
                newAccount.getId(),
                newAccount.getCreatedAt(),
                newAccount.getDocumentNumber().getNumber()));
    }
}
