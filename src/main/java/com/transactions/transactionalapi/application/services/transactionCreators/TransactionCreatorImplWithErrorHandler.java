package com.transactions.transactionalapi.application.services.transactionCreators;

import com.transactions.transactionalapi.application.models.CommandResult;
import com.transactions.transactionalapi.application.models.CreateTransactionModel;
import com.transactions.transactionalapi.application.models.CreatedTransactionModel;
import com.transactions.transactionalapi.application.services.accountCreators.AccountCreatorImplWithErrorHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Primary
@Service
@Qualifier("transactionCreatorImplWithErrorHandler")
public class TransactionCreatorImplWithErrorHandler implements TransactionCreator {
    private final TransactionCreator transactionCreator;

    private final Logger logger;

    public TransactionCreatorImplWithErrorHandler(
            @Qualifier("transactionCreatorImplWithAccountExistsValidation") TransactionCreator transactionCreator,
            Logger logger) {
        if (transactionCreator == null)
            throw new IllegalArgumentException("transactionCreator must not be null");

        if (logger == null)
            throw new IllegalArgumentException("logger must not be null");

        this.transactionCreator = transactionCreator;
        this.logger = logger;
    }

    @Override
    public CommandResult<CreatedTransactionModel> create(CreateTransactionModel transactionModel) {
        try {
            return transactionCreator.create(transactionModel);
        } catch (Exception ex) {
            logger.error("An error has occurred while creating transaction", ex);
            throw ex;
        }
    }
}
