package com.transactions.transactionalapi.infrastructure.respositories;

import com.transactions.transactionalapi.domain.entities.Account;
import com.transactions.transactionalapi.domain.entities.CreditLimit;
import com.transactions.transactionalapi.domain.repositories.AccountReader;
import com.transactions.transactionalapi.domain.repositories.CreditLimitRepository;
import com.transactions.transactionalapi.infrastructure.dtos.CreditLimitDto;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class CreditLimitRepositoryImpl implements CreditLimitRepository {
    private CreditLimitJpaRepository creditLimitJpaRepository;

    private AccountReader accountReader;

    public CreditLimitRepositoryImpl(CreditLimitJpaRepository creditLimitJpaRepository, AccountReader accountReader) {
        this.accountReader = accountReader;
        if (creditLimitJpaRepository == null)
            throw new IllegalArgumentException("creditLimitJpaRepository must not be null");

        this.creditLimitJpaRepository = creditLimitJpaRepository;
    }

    public CreditLimit create(CreditLimit creditLimit) {
        var creditLimitDto = new CreditLimitDto(creditLimit.getId(), creditLimit.getAccount().getId(), creditLimit.getCreatedAt(), creditLimit.getLimit());

        creditLimitJpaRepository.save(creditLimitDto);

        return creditLimit;
    }

    public Optional<CreditLimit> oneByAccountId(UUID accountId) {
        var account = accountReader.one(accountId);

        return creditLimitJpaRepository
                .findByAccountId(accountId)
                .map(creditLimitDto ->
                        new CreditLimit(
                                creditLimitDto.getId(),
                                creditLimitDto.getCreatedAt(),
                                account.get(),
                                creditLimitDto.getLimit()));
    }

    @Override
    public CreditLimit update(CreditLimit creditLimit) {
        var creditLimitDto = new CreditLimitDto(
                creditLimit.getId(),
                creditLimit.getAccount().getId(),
                creditLimit.getCreatedAt(),
                creditLimit.getLimit());

        creditLimitJpaRepository.saveAndFlush(creditLimitDto);

        return creditLimit;
    }
}
