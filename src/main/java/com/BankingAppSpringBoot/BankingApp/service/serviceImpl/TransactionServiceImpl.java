package com.BankingAppSpringBoot.BankingApp.service.serviceImpl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.BankingAppSpringBoot.BankingApp.dto.TransactionDto;
import com.BankingAppSpringBoot.BankingApp.entity.Transaction;
import com.BankingAppSpringBoot.BankingApp.entity.Transaction.TransactionType;
import com.BankingAppSpringBoot.BankingApp.mapper.TransactionMapper;
import com.BankingAppSpringBoot.BankingApp.repository.AccountRepository;
import com.BankingAppSpringBoot.BankingApp.repository.TransactionRepository;
import com.BankingAppSpringBoot.BankingApp.service.TransactionService;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;



@Service
public class TransactionServiceImpl implements TransactionService{

    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }



        @Override
         public List<TransactionDto> getAllTransactions(){
            return transactionRepository.findAll()
                        .stream().map(TransactionMapper::toDto)
                        .collect(Collectors.toList());
            
             
         };


    @Override
     public List<TransactionDto> getTransactionsByRib(String rib ){
        
        return transactionRepository.findByAccountRib(rib)
                .stream()
                .map(TransactionMapper::toDto)
                .collect(Collectors.toList())
                ;
       
     }

     @Override
     public List<TransactionDto> getTransactionsAccountId(Long accountId ){
        return transactionRepository.findByAccountId(accountId)
        .stream()
                .map(TransactionMapper::toDto)
                .collect(Collectors.toList())
                ;
     }



     @Override
     public List<TransactionDto> getTransactionsByDate(Long accountId , LocalDateTime start, LocalDateTime end){
        return transactionRepository.findByAccountIdAndTimestampBetween(accountId, start, end)
                .stream()
                .map((TransactionMapper::toDto))
                .collect(Collectors.toList());
        
     };


     @Override
         public List<TransactionDto> getTransactionsByAmount(Long accountId , BigDecimal min, BigDecimal max){
            return transactionRepository.findByAccountIdAndAmountBetween(accountId, min, max)
            .stream()
            .map((TransactionMapper::toDto))
            .collect(Collectors.toList())
            
            ;
         };

             @Override
             public List<TransactionDto> getTransactionsByType(Long accountId , TransactionType type){
                return transactionRepository.findByAccountIdAndType(accountId, type)
                .stream()
            .map((TransactionMapper::toDto))
            .collect(Collectors.toList())
            ;
             } ;



}
