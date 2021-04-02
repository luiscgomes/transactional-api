package com.transactions.transactionalapi.infrastructure.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "accounts")
@Data
@AllArgsConstructor
public class AccountDto {
    @Id
    @Column(name = "account_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "document_number")
    private String documentNumber;

    public AccountDto(LocalDateTime createdAt, String documentNumber) {
        this.createdAt = createdAt;
        this.documentNumber = documentNumber;
    }
}
