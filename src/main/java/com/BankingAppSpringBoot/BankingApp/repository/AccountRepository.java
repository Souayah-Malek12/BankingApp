package com.BankingAppSpringBoot.BankingApp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.BankingAppSpringBoot.BankingApp.entity.Account;
import com.BankingAppSpringBoot.BankingApp.service.UserService;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long>{

    Optional<Account> findByRib(String rib);
}
