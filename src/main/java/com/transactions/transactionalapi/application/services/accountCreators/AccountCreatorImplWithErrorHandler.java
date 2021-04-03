package com.transactions.transactionalapi.application.services.accountCreators;

import com.transactions.transactionalapi.application.models.CommandResult;
import com.transactions.transactionalapi.application.models.CreateAccountModel;
import com.transactions.transactionalapi.application.models.CreatedAccountModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
            @Qualifier("accountCreateWithDocumentNumberAlreadyExits") AccountCreator accountCreator) {
        if (accountCreator == null)
            throw new IllegalArgumentException("accountCreator must not be null");

        this.accountCreator = accountCreator;
        logger = LoggerFactory.getLogger(AccountCreatorImplWithErrorHandler.class);
    }

    @Override
    public CommandResult<CreatedAccountModel> create(CreateAccountModel account) {
        try {
            return accountCreator.create(account);
        } catch (Exception ex) {
            logger.error("An error has occurred while creating account");
            throw ex;
        }
    }
}
