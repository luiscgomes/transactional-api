package com.transactions.transactionalapi.domain.repositories;

import com.transactions.transactionalapi.domain.entities.Account;

public interface AccountWriter {
    Account create(Account account);
}
