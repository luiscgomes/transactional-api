package com.transactions.transactionalapi.domain.repositories;

import com.transactions.transactionalapi.domain.entities.CreditLimit;

import java.util.Optional;
import java.util.UUID;

public interface CreditLimitRepository {
    CreditLimit create(CreditLimit creditLimit);

    Optional<CreditLimit> oneByAccountId(UUID accountId);

    CreditLimit update(CreditLimit creditLimit);
}
