package com.transactions.transactionalapi.infrastructure.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "creditlimits")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditLimitDto {
    @Id
    @Getter
    @Column(name = "credit_limit_id")
    private UUID id;

    @Getter
    @Column(name = "account_id")
    private UUID accountId;

    @Getter
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Getter
    @Column(name = "credit_limit")
    private BigDecimal limit;
}
