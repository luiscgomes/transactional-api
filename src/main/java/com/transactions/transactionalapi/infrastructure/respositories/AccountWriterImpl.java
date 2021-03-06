package com.transactions.transactionalapi.infrastructure.respositories;

import com.transactions.transactionalapi.domain.entities.Account;
import com.transactions.transactionalapi.domain.repositories.AccountWriter;
import com.transactions.transactionalapi.infrastructure.dtos.AccountDto;
import org.springframework.stereotype.Repository;

@Repository
public class AccountWriterImpl implements AccountWriter {
    private final AccountJpaRepository accountJpaRepository;

    public AccountWriterImpl(AccountJpaRepository accountJpaRepository) {
        if (accountJpaRepository == null)
            throw new IllegalArgumentException("accountJpaRepository must not be null");

        this.accountJpaRepository = accountJpaRepository;
    }

    @Override
    public Account create(Account account) {
        var accountDto = new AccountDto(account.getId(), account.getCreatedAt(), account.getDocumentNumber().getNumber());

        var newAccount = accountJpaRepository.save(accountDto);

        return new Account(
                newAccount.getId(),
                newAccount.getDocumentNumber(),
                newAccount.getCreatedAt()
        );
    }
}
