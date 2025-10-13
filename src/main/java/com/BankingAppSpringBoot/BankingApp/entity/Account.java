package com.BankingAppSpringBoot.BankingApp.entity;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name="rib", unique = true, nullable = false, length=24)
    private String rib;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private AccountType type;

    @Column(name = "account_holder_name")
    private String accountHolderName;

    @Column(name = "balance")
    private BigDecimal balance;




    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="user_id", nullable= false)
    @JsonIgnore // prevent serializing the user inside Account
    private User user ;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
private Set<Transaction> transactions = new HashSet<>();





    // Default constructor
    public Account() {
    }

    // Parameterized constructor
    public Account(Long id, String rib, AccountType type,  String accountHolderName, BigDecimal balance) {
        this.id = id;
        this.rib = rib;
        this.type = type;
        this.accountHolderName = accountHolderName;
        this.balance = balance;
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

    public Set<Transaction> getTransactions(){
        return this.transactions;

    }

    public void setTransactions(Set<Transaction> transactions){
        this.transactions = transactions; 
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", accountHolderName='" + accountHolderName + '\'' +
                ", balance=" + balance +
                '}';
    }


    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    

    public enum AccountType{
        SAVINGS,CURRENT, BUSINESS
    }
}
