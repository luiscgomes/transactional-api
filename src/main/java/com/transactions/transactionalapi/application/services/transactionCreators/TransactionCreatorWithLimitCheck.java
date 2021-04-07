package com.transactions.transactionalapi.application.services.transactionCreators;

import com.transactions.transactionalapi.application.models.CommandResult;
import com.transactions.transactionalapi.application.models.CreateTransactionModel;
import com.transactions.transactionalapi.application.models.CreatedTransactionModel;
import com.transactions.transactionalapi.domain.entities.CreditLimit;
import com.transactions.transactionalapi.domain.entities.Transaction;
import com.transactions.transactionalapi.domain.enums.OperationTypes;
import com.transactions.transactionalapi.domain.repositories.CreditLimitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Qualifier("transactionCreatorWithLimitCheck")
public class TransactionCreatorWithLimitCheck implements TransactionCreator {
    @Autowired
    private CreditLimitRepository creditLimitRepository;

    @Autowired
    @Qualifier("transactionCreatorImplWithAccountExistsValidation")
    private TransactionCreator transactionCreator;

    @Override
    public CommandResult<CreatedTransactionModel> create(CreateTransactionModel transactionModel) {
        var creditLimit = creditLimitRepository.oneByAccountId(transactionModel.getAccountId());

        var newLimit = newCreditLimit(transactionModel, creditLimit.get());

        if (newLimit.isEmpty()) {
            var errorCommandResult = new CommandResult<CreatedTransactionModel>();
            errorCommandResult.addError("account does not have limit");

            return errorCommandResult;
        }

        creditLimitRepository.update(newLimit.get());

        return transactionCreator.create(transactionModel);
    }

    private Optional<CreditLimit> newCreditLimit(CreateTransactionModel transactionModel, CreditLimit limit) {
        var operationType = OperationTypes.getOperationTypeValue(transactionModel.getOperationTypeId());

        if (Transaction.operationsWithNegativeAmount().contains(operationType)) {
            if (limit.getLimit().compareTo(transactionModel.getAmount()) >= 0) {
                limit.subtractLimit(transactionModel.getAmount());
            } else {
                return Optional.empty();
            }
        } else {
            limit.addLimit(transactionModel.getAmount());
        }

        return Optional.of(limit);
    }
}
