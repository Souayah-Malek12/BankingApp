package com.BankingAppSpringBoot.BankingApp.service;

import java.math.BigDecimal;
import java.util.List;

import com.BankingAppSpringBoot.BankingApp.dto.AccountDto;

public interface AccountService {
        AccountDto createAccount(AccountDto accountDto);
        AccountDto getAccountById(Long id);
        
        AccountDto deposit(String rib , BigDecimal amount);
        AccountDto withDraw(String rib, BigDecimal amount);
        void transferMoney(String senderRib, String receiverRib, BigDecimal amount);

        void deleteAccount(Long id);
       List<AccountDto> getAllAccounts();

}
