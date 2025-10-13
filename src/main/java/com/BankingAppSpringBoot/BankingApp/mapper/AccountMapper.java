package com.BankingAppSpringBoot.BankingApp.mapper;

import java.util.stream.Collectors;

import com.BankingAppSpringBoot.BankingApp.dto.AccountDto;
import com.BankingAppSpringBoot.BankingApp.entity.Account;
import com.BankingAppSpringBoot.BankingApp.entity.Transaction;
import com.BankingAppSpringBoot.BankingApp.entity.User;
import com.BankingAppSpringBoot.BankingApp.repository.UserRepository;

public class AccountMapper {

    private UserRepository userRepository;
    // Convert Entity -> DTO
    public static AccountDto toDto(Account account) {
        if (account == null) return null;

        AccountDto dto = new AccountDto();
        dto.setId(account.getId());
        dto.setRib(account.getRib());
        dto.setType(account.getType());
        dto.setAccountHolderName(account.getAccountHolderName());
        dto.setBalance(account.getBalance());

        if (account.getUser() != null) {
            dto.setUserId(account.getUser().getId());
        }

        if (account.getTransactions() != null && !account.getTransactions().isEmpty()) {
            dto.setTransactions(
                account.getTransactions().stream()
                        .map(TransactionMapper::toDto)
                        .collect(Collectors.toSet())
            );
        }

        return dto;
    }

    // Convert DTO -> Entity
    public static Account toEntity(AccountDto dto) {
        if (dto == null) return null;

        Account account = new Account();
        account.setId(dto.getId());
        account.setRib(dto.getRib());
        account.setType(dto.getType());
        account.setAccountHolderName(dto.getAccountHolderName());
        account.setBalance(dto.getBalance());
        // We’ll set User in the service, not here (since it’s another entity)
        
        if (dto.getTransactions() != null && !dto.getTransactions().isEmpty()) {
            account.setTransactions(
                dto.getTransactions().stream()
                        .map(tx -> TransactionMapper.toEntity(tx, account))
                        .collect(Collectors.toSet())
            );
        }

        return account;
    }
}
