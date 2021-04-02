package com.transactions.transactionalapi.infrastructure.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "Accounts")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {
    @Id
    @Column(name = "AccountId")
    private UUID id;

    @Column(name = "CreatedAt")
    private LocalDateTime createdAt;

    @Column(name = "DocumentNumber")
    private String documentNumber;
}
