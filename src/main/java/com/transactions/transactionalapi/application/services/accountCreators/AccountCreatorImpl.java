package com.transactions.transactionalapi.application.services.accountCreators;

import com.transactions.transactionalapi.application.models.CommandResult;
import com.transactions.transactionalapi.application.models.CreatedAccountModel;
import com.transactions.transactionalapi.application.models.CreateAccountModel;
import com.transactions.transactionalapi.domain.entities.Account;
import com.transactions.transactionalapi.domain.repositories.AccountWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("accountCreatorBase")
public class AccountCreatorImpl implements AccountCreator {
    private final AccountWriter accountWriter;

    public AccountCreatorImpl(AccountWriter accountWriter) {
        if (accountWriter == null)
            throw new IllegalArgumentException("accountWriter must not be null");

        this.accountWriter = accountWriter;
    }

    @Override
    public CommandResult<CreatedAccountModel> create(CreateAccountModel accountModel) {
        var account = new Account(accountModel.getDocumentNumber());

        var newAccount = accountWriter.create(account);

        return new CommandResult<>(new CreatedAccountModel(
                newAccount.getId(),
                newAccount.getCreatedAt(),
                newAccount.getDocumentNumber().getNumber()));
    }
}
