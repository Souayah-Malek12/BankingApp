package com.BankingAppSpringBoot.BankingApp.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.BankingAppSpringBoot.BankingApp.service.TransactionService;
import com.BankingAppSpringBoot.BankingApp.dto.TransactionDto;
import com.BankingAppSpringBoot.BankingApp.entity.Transaction.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/api/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService){
        this.transactionService= transactionService;
    }


    @GetMapping
    public List<TransactionDto> getAll(){
        return transactionService.getAllTransactions();
    }


    @GetMapping("/{rib}")
    public List<TransactionDto> getbyRib(@PathVariable String rib){
        return transactionService.getTransactionsByRib(rib);
    }
    

    //GET /api/transactions/5/date?start=2025-10-01T00:00:00&end=2025-10-08T23:59:59

    @GetMapping("/{accountId}/date")
    public  List<TransactionDto> getTransactionByDate(@PathVariable Long accountId,@RequestParam LocalDateTime start,@RequestParam LocalDateTime end){

        return this.transactionService.getTransactionsByDate(accountId, start, end);
    }


    //http://localhost:8080/api/transaction/11/amount?min=100&max=500

    @GetMapping("/{accountId}/amount")
         public List<TransactionDto> getTransactionsByAmount(@PathVariable Long accountId,
          @RequestParam BigDecimal min,@RequestParam BigDecimal max){
        
            return this.transactionService.getTransactionsByAmount(accountId, min, max);

    }
    

    //GET http://localhost:8080/api/transaction/10/type?type=DEPOSIT

    @GetMapping("/{accountId}/{type}")
    public List<TransactionDto> getTransactionsByType(
            @PathVariable Long accountId,
            @PathVariable TransactionType type) {
        return this.transactionService.getTransactionsByType(accountId, type);
    }
    

    


}
