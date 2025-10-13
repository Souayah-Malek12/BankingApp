package com.BankingAppSpringBoot.BankingApp.mapper;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.BankingAppSpringBoot.BankingApp.dto.UserDto;
import com.BankingAppSpringBoot.BankingApp.entity.Account;
import com.BankingAppSpringBoot.BankingApp.entity.User;
import com.BankingAppSpringBoot.BankingApp.repository.AccountRepository;


@Component
public class UserMapper {




    public  UserDto toDto(User user){

        if( user == null){
            return null; 
        }

        UserDto userDto = new UserDto();
            userDto.setId(user.getId());
            userDto.setFullName(user.getFullName());
            userDto.setEmail(user.getEmail());
            userDto.setPhoneNumber(user.getPhoneNumber());
            userDto.setAddress(user.getAddress());
            userDto.setRole(user.getRole());

            Set<Account> accounts = null;


            userDto.setAccounts(user.getAccounts());

        return userDto;
    }

        // Suppose you have an AccountRepository
        @Autowired
        private AccountRepository accountRepository;
        
        public User toEntity(UserDto userDto) {
            if (userDto == null) return null;
        
            User user = new User();
            user.setId(userDto.getId());
            user.setFullName(userDto.getFullName());
            user.setEmail(userDto.getEmail());
            user.setPassword(userDto.getPassword());
            user.setPhoneNumber(userDto.getPhoneNumber());
            user.setAddress(userDto.getAddress());
            user.setRole(userDto.getRole());
            user.setAccounts(userDto.getAccounts());

            
        
            return user;
        }

    



    

}

