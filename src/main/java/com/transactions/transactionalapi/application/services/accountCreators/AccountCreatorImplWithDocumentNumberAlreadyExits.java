package com.transactions.transactionalapi.application.services.accountCreators;

import com.transactions.transactionalapi.application.models.CommandResult;
import com.transactions.transactionalapi.application.models.CreateAccountModel;
import com.transactions.transactionalapi.application.models.CreatedAccountModel;
import com.transactions.transactionalapi.domain.repositories.AccountReader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("accountCreateWithDocumentNumberAlreadyExits")
public class AccountCreatorImplWithDocumentNumberAlreadyExits implements AccountCreator {
    private final AccountCreator accountCreator;

    private final AccountReader accountReader;

    public AccountCreatorImplWithDocumentNumberAlreadyExits(
            @Qualifier("accountCreatorBase") AccountCreator accountCreator,
            AccountReader accountReader) {
        if (accountCreator == null)
            throw new IllegalArgumentException("accountCreator must not be null");

        if (accountReader == null)
            throw new IllegalArgumentException("accountReader must not be null");

        this.accountCreator = accountCreator;
        this.accountReader = accountReader;
    }

    @Override
    public CommandResult<CreatedAccountModel> create(CreateAccountModel account) {
        var createdAccount = accountReader.oneByDocumentNumber(account.getDocumentNumber());

        if (createdAccount.isPresent()) {
            var commandResult = new CommandResult<CreatedAccountModel>();
            commandResult.addError("Document number must be unique. There is already an account with provided document number");

            return commandResult;
        }

        return accountCreator.create(account);
    }
}
