package com.BankingAppSpringBoot.BankingApp.repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.BankingAppSpringBoot.BankingApp.entity.Transaction;
import com.BankingAppSpringBoot.BankingApp.entity.Transaction.TransactionType;

public interface TransactionRepository extends JpaRepository<Transaction, Long>{


    List<Transaction> findByAccountRib(String rib );

    List<Transaction> findByAccountId(Long accountId );
    List<Transaction> findByAccountIdAndTimestampBetween(Long accountId , LocalDateTime start, LocalDateTime end);
    List<Transaction> findByAccountIdAndAmountBetween(Long accountId  , BigDecimal min, BigDecimal max);
    List<Transaction> findByAccountIdAndType(Long accountId  , TransactionType type);


}
