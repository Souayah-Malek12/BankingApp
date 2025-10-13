package com.BankingAppSpringBoot.BankingApp.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.BankingAppSpringBoot.BankingApp.dto.TransactionDto;
import com.BankingAppSpringBoot.BankingApp.entity.Transaction.TransactionType;

public interface TransactionService {


    public List<TransactionDto> getAllTransactions();

    public List<TransactionDto> getTransactionsByRib(String rib );

    public List<TransactionDto> getTransactionsAccountId(Long accountId );
    
    public List<TransactionDto> getTransactionsByDate(Long accountId , LocalDateTime start, LocalDateTime end);
    
    public List<TransactionDto> getTransactionsByAmount(Long accountId , BigDecimal min, BigDecimal max);

    public List<TransactionDto> getTransactionsByType(Long accountId , TransactionType type) ;
}
