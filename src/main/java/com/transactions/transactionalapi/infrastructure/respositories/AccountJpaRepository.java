package com.transactions.transactionalapi.infrastructure.respositories;

import com.transactions.transactionalapi.infrastructure.dtos.AccountDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AccountJpaRepository extends JpaRepository<AccountDto, UUID> {
    Optional<AccountDto> findByDocumentNumber(String documentNumber);
}
