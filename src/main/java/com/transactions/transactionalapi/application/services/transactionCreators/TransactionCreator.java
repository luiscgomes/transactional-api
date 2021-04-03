package com.transactions.transactionalapi.application.services.transactionCreators;

import com.transactions.transactionalapi.application.models.CommandResult;
import com.transactions.transactionalapi.application.models.CreateTransactionModel;
import com.transactions.transactionalapi.application.models.CreatedTransactionModel;

public interface TransactionCreator {
    CommandResult<CreatedTransactionModel> create(CreateTransactionModel transactionModel);
}
