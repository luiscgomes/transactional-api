package com.transactions.transactionalapi.application.services.creditLimitCreators;

import com.transactions.transactionalapi.application.models.CreateCreditLimitModel;
import com.transactions.transactionalapi.application.models.CreatedCreditLimitModel;
import com.transactions.transactionalapi.domain.entities.CreditLimit;
import com.transactions.transactionalapi.domain.repositories.AccountReader;
import com.transactions.transactionalapi.domain.repositories.CreditLimitRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class CreditLimitCreatorImpl implements CreditLimitCreator{
    private CreditLimitRepository creditLimitRepository;

    private AccountReader accountReader;

    public CreditLimitCreatorImpl(CreditLimitRepository creditLimitRepository, AccountReader accountReader) {
        this.creditLimitRepository = creditLimitRepository;
        this.accountReader = accountReader;
    }

    public Optional<CreatedCreditLimitModel> create(CreateCreditLimitModel creditLimitModel, UUID accountId) {
        var account = accountReader.one(accountId);

        var creditLimit = new CreditLimit(account.get(), creditLimitModel.getLimit());

        creditLimitRepository.create(creditLimit);

        return Optional.of(new CreatedCreditLimitModel(
                creditLimit.getId(),
                creditLimit.getCreatedAt(),
                creditLimit.getAccount().getId(),
                creditLimit.getLimit()
        ));
    }
}
