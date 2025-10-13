package com.BankingAppSpringBoot.BankingApp.dto;

import java.util.Set;

import com.BankingAppSpringBoot.BankingApp.entity.Account;
import com.BankingAppSpringBoot.BankingApp.entity.User;
import com.BankingAppSpringBoot.BankingApp.entity.User.Role;

public class UserDto {


    private Long id;

    private String fullName;

    private String email;

    private String password;

    private String phoneNumber;

    private String address;

    private Role role;


    private Set<Account> accounts;

    public UserDto(){}

    public UserDto(Long id, String fullName, String email, String password,String phoneNumber, String address, Role role, Set<Account> accounts) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.role = role;
        this.accounts = accounts;
    }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }

    public Set<Account> getAccounts() { return accounts; }
    public void setAccounts(Set<Account> accounts) { this.accounts = accounts; }



}
