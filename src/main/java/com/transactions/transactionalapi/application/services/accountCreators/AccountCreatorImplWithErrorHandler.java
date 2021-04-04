package com.transactions.transactionalapi.application.services.accountCreators;

import com.transactions.transactionalapi.application.models.CommandResult;
import com.transactions.transactionalapi.application.models.CreateAccountModel;
import com.transactions.transactionalapi.application.models.CreatedAccountModel;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Primary
@Service
@Qualifier("accountCreatorImplWithErrorHandler")
public class AccountCreatorImplWithErrorHandler implements AccountCreator {
    private final AccountCreator accountCreator;

    private final Logger logger;

    public AccountCreatorImplWithErrorHandler(
            @Qualifier("accountCreateWithDocumentNumberAlreadyExits") AccountCreator accountCreator, Logger logger) {
        if (accountCreator == null)
            throw new IllegalArgumentException("accountCreator must not be null");

        if (logger == null)
            throw new IllegalArgumentException("logger must not be null");


        this.logger = logger;
        this.accountCreator = accountCreator;
    }

    @Override
    public CommandResult<CreatedAccountModel> create(CreateAccountModel account) {
        try {
            return accountCreator.create(account);
        } catch (Exception ex) {
            logger.error("An error has occurred while creating account", ex);
            throw ex;
        }
    }
}
