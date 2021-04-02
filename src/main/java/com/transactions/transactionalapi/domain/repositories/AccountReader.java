package com.transactions.transactionalapi.domain.repositories;

import com.transactions.transactionalapi.domain.entities.Account;

import java.util.Optional;
import java.util.UUID;

public interface AccountReader {
    Optional<Account> one(UUID accountId);
}
