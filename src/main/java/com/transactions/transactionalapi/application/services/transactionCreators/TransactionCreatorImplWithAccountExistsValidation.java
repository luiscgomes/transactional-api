package com.transactions.transactionalapi.application.services.transactionCreators;

import com.transactions.transactionalapi.application.models.CommandResult;
import com.transactions.transactionalapi.application.models.CreateTransactionModel;
import com.transactions.transactionalapi.application.models.CreatedTransactionModel;
import com.transactions.transactionalapi.domain.repositories.AccountReader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Primary
@Service
@Qualifier("TransactionCreatorImplWithAccountExistsValidation")
public class TransactionCreatorImplWithAccountExistsValidation implements TransactionCreator {
    private final TransactionCreator transactionCreator;

    private final AccountReader accountReader;

    public TransactionCreatorImplWithAccountExistsValidation(
            @Qualifier("transactionCreatorBase")
            TransactionCreator transactionCreator,
            AccountReader accountReader) {
        if (transactionCreator == null)
            throw new IllegalArgumentException("transactionCreator must not be null");

        if (accountReader == null)
            throw new IllegalArgumentException("accountReader must not be null");

        this.transactionCreator = transactionCreator;
        this.accountReader = accountReader;
    }

    @Override
    public CommandResult<CreatedTransactionModel> create(CreateTransactionModel transactionModel) {
        var account = accountReader.one(transactionModel.getAccountId());

        if (account.isEmpty()) {
            var errorResult = new CommandResult<CreatedTransactionModel>();
            errorResult.addError("Account does not exit for provided account id");

            return errorResult;
        }

        transactionModel.setAccount(account.get());

        return transactionCreator.create(transactionModel);
    }
}
