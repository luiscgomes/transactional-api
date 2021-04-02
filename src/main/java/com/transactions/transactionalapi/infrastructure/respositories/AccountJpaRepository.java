package com.transactions.transactionalapi.infrastructure.respositories;

import com.transactions.transactionalapi.infrastructure.dtos.AccountDto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountJpaRepository extends JpaRepository<AccountDto, Long> {
}
