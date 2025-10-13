package com.BankingAppSpringBoot.BankingApp.controller;

import com.BankingAppSpringBoot.BankingApp.dto.AccountDto;
import com.BankingAppSpringBoot.BankingApp.dto.TransferRequest;
import com.BankingAppSpringBoot.BankingApp.service.AccountService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;




@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<AccountDto> addAccount(@RequestBody AccountDto accountDto) {
        System.out.println("Received request to create account: " + accountDto);
        AccountDto createdAccount = accountService.createAccount(accountDto);
        System.out.println("Created account: " + createdAccount);
        return new ResponseEntity<>(createdAccount, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountDto> getAccountById(@PathVariable Long id){

        AccountDto accountDto = accountService.getAccountById(id);

        return ResponseEntity.ok(accountDto);
    }

    @PutMapping("/deposit/{rib}")
    public ResponseEntity<AccountDto> deposit(@PathVariable String rib,@RequestBody Map<String ,BigDecimal> request) {

        BigDecimal amount = request.get("amount");
        AccountDto accountDto = accountService.deposit(rib, amount);
        return ResponseEntity.ok(accountDto);
    }

    @PutMapping("/withdraw/{rib}")
    public ResponseEntity<AccountDto> withdraw(@PathVariable String rib, @RequestBody Map<String, BigDecimal> request) {
        //TODO: process PUT request
        BigDecimal amount = request.get("amount");
        AccountDto accountDto = accountService.withDraw(rib, amount);
        
        return ResponseEntity.ok(accountDto);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){

        accountService.deleteAccount(id);

        return ResponseEntity.ok("Account deleted successfully");
    }

    @GetMapping()
    public ResponseEntity<List<AccountDto>> getAllAccounts() {

       List<AccountDto> accountDtos =  accountService.getAllAccounts();
        return ResponseEntity.ok(accountDtos);
    }

    @PutMapping("/transferMoney")
    public ResponseEntity<String> transferMoney(@RequestBody TransferRequest request) {
        //TODO: process PUT request

        accountService.transferMoney(request.getSenderRib(), request.getReceiverRib(), request.getAmount());

        return ResponseEntity.ok("Transfer Succesfully with amount of"+request.getAmount());
    }
    
}
