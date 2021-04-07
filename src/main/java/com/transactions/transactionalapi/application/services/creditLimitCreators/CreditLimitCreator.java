package com.transactions.transactionalapi.application.services.creditLimitCreators;

import com.transactions.transactionalapi.application.models.CreateCreditLimitModel;
import com.transactions.transactionalapi.application.models.CreatedCreditLimitModel;

import java.util.Optional;
import java.util.UUID;

public interface CreditLimitCreator {
    Optional<CreatedCreditLimitModel> create(CreateCreditLimitModel creditLimitModel, UUID accountId);
}
