package com.transactions.transactionalapi.infrastructure.respositories;

import com.transactions.transactionalapi.infrastructure.dtos.TransactionDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransactionJpaRepository extends JpaRepository<TransactionDto, UUID> {
}
