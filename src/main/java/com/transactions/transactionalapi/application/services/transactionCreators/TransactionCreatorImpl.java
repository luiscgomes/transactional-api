package com.transactions.transactionalapi.application.services.transactionCreators;

import com.transactions.transactionalapi.application.models.CommandResult;
import com.transactions.transactionalapi.application.models.CreateTransactionModel;
import com.transactions.transactionalapi.application.models.CreatedTransactionModel;
import com.transactions.transactionalapi.domain.entities.Transaction;
import com.transactions.transactionalapi.domain.enums.OperationTypes;
import com.transactions.transactionalapi.domain.repositories.TransactionWriter;
import com.transactions.transactionalapi.domain.services.TransactionOperationAmountConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("transactionCreatorBase")
public class TransactionCreatorImpl implements TransactionCreator {
    private final TransactionWriter transactionWriter;

    private final TransactionOperationAmountConverter transactionOperationAmountConverter;

    public TransactionCreatorImpl(
            TransactionWriter transactionWriter,
            TransactionOperationAmountConverter transactionOperationAmountConverter) {
        if (transactionWriter == null)
            throw new IllegalArgumentException("transactionWriter must not be null");

        if (transactionOperationAmountConverter == null)
            throw new IllegalArgumentException("transactionOperationAmountConverter must not be null");

        this.transactionWriter = transactionWriter;
        this.transactionOperationAmountConverter = transactionOperationAmountConverter;
    }

    @Override
    public CommandResult<CreatedTransactionModel> create(CreateTransactionModel transactionModel) {
        var operationType = OperationTypes.getOperationTypeValue(transactionModel.getOperationTypeId());
        var transactionAmount = transactionOperationAmountConverter.convert(transactionModel.getAmount(), operationType);

        var transaction = new Transaction(
                transactionModel.getAccount(),
                operationType,
                transactionAmount);

        var createdTransaction = transactionWriter.create(transaction);

        return new CommandResult<>(new CreatedTransactionModel(
                createdTransaction.getId(),
                createdTransaction.getEventDate(),
                transaction.getAccount().getId(),
                createdTransaction.getOperationType().getOperationTypeId(),
                createdTransaction.getOperationType().name(),
                createdTransaction.getAmount().abs()
        ));
    }
}
