package com.BankingAppSpringBoot.BankingApp.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.BankingAppSpringBoot.BankingApp.entity.Account;
import com.BankingAppSpringBoot.BankingApp.entity.Transaction.TransactionType;

public class TransactionDto {

    private Long id;
    private Long accountId;
    private BigDecimal amount;
    private TransactionType type;
    private LocalDateTime timestamp;

    // No-arg constructor
    public TransactionDto() {
    }

    // All-args constructor
    public TransactionDto(Long id, Long accountId, BigDecimal amount, TransactionType type, LocalDateTime timestamp) {
        this.id = id;
        this.accountId = accountId;
        this.amount = amount;
        this.type = type;
        this.timestamp = timestamp;
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getAccountId() { return accountId; }
    public void setAccountId(Long accountId) { this.accountId = accountId; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public TransactionType getType() { return type; }
    public void setType(TransactionType type) { this.type = type; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}
