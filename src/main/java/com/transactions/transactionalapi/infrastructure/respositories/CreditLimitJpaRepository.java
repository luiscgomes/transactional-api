package com.transactions.transactionalapi.infrastructure.respositories;

import com.transactions.transactionalapi.infrastructure.dtos.AccountDto;
import com.transactions.transactionalapi.infrastructure.dtos.CreditLimitDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CreditLimitJpaRepository extends JpaRepository<CreditLimitDto, UUID> {
    Optional<CreditLimitDto> findByAccountId(UUID accountId);
}
