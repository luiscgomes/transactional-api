package com.transactions.transactionalapi.infrastructure.respositories;

import com.transactions.transactionalapi.domain.entities.Account;
import com.transactions.transactionalapi.domain.repositories.AccountReader;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class AccountReaderImpl implements AccountReader {
    private final AccountJpaRepository accountJpaRepository;

    public AccountReaderImpl(AccountJpaRepository accountJpaRepository) {
        if (accountJpaRepository == null)
            throw new IllegalArgumentException("accountJpaRepository must not be null");

        this.accountJpaRepository = accountJpaRepository;
    }

    @Override
    public Optional<Account> one(UUID accountId) {
        return accountJpaRepository
                .findById(accountId)
                .map(accountDto -> new Account(
                        accountDto.getId(),
                        accountDto.getDocumentNumber(),
                        accountDto.getCreatedAt()));

    }

    @Override
    public Optional<Account> oneByDocumentNumber(String documentNumber) {
        return accountJpaRepository
                .findByDocumentNumber(documentNumber)
                .map(accountDto -> new Account(
                        accountDto.getId(),
                        accountDto.getDocumentNumber(),
                        accountDto.getCreatedAt()));
    }
}
