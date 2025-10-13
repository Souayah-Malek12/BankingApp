package com.BankingAppSpringBoot.BankingApp.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.BankingAppSpringBoot.BankingApp.dto.TransactionDto;
import com.BankingAppSpringBoot.BankingApp.entity.Account;
import com.BankingAppSpringBoot.BankingApp.entity.Transaction;

public class TransactionMapper {

    // 🔹 Entity → DTO
    public static TransactionDto toDto(Transaction transaction) {
        if (transaction == null) {
            return null;
        }

        TransactionDto dto = new TransactionDto();
        dto.setId(transaction.getId());
        dto.setAccountId(transaction.getAccount().getId()); // full account entity
        dto.setAmount(transaction.getAmount());
        dto.setType(transaction.getType());
        dto.setTimestamp(transaction.getTimestamp());
        return dto;
    }

    // 🔹 DTO → Entity
    public static Transaction toEntity(TransactionDto dto, Account account) {
        if (dto == null) {
            return null;
        }

        Transaction transaction = new Transaction();
        transaction.setId(dto.getId());
        transaction.setAccount(account); // full account entity
        transaction.setAmount(dto.getAmount());
        transaction.setType(dto.getType());
        transaction.setTimestamp(dto.getTimestamp());
        return transaction;
    }

   
}
