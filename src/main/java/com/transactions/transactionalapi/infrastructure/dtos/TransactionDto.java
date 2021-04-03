package com.transactions.transactionalapi.infrastructure.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "transactions")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDto {
    @Id
    @Column(name = "transaction_id")
    private UUID id;

    @Column(name = "event_date")
    private LocalDateTime eventDate;

    @Column(name = "account_id")
    private UUID accountId;

    @Column(name = "operation_type_id")
    private int operationTypeId;

    @Column(name = "amount")
    private BigDecimal amount;
}
