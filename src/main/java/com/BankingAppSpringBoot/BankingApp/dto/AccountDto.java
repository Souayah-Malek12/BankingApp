package com.BankingAppSpringBoot.BankingApp.dto;

import java.math.BigDecimal;
import java.util.Set;

import com.BankingAppSpringBoot.BankingApp.entity.Account.AccountType;

public class AccountDto {

    private Long id;
    private String rib;
    private AccountType type;
    private String accountHolderName;
    private BigDecimal balance;
    private Long userId; // store only user ID, not full User object
    private Set<TransactionDto> transactions; // optional: for nested data transfer

    // Constructors
    public AccountDto() {
    }

    public AccountDto(Long id, String rib, AccountType type, String accountHolderName,
                      BigDecimal balance, Long userId, Set<TransactionDto> transactions) {
        this.id = id;
        this.rib = rib;
        this.type = type;
        this.accountHolderName = accountHolderName;
        this.balance = balance;
        this.userId = userId;
        this.transactions = transactions;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRib() {
        return rib;
    }

    public void setRib(String rib) {
        this.rib = rib;
    }

    public AccountType getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type = type;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Set<TransactionDto> getTransactions() {
        return transactions;
    }

    public void setTransactions(Set<TransactionDto> transactions) {
        this.transactions = transactions;
    }

    @Override
    public String toString() {
        return "AccountDto{" +
                "id=" + id +
                ", rib='" + rib + '\'' +
                ", type=" + type +
                ", accountHolderName='" + accountHolderName + '\'' +
                ", balance=" + balance +
                ", userId=" + userId +
                '}';
    }
}
