package com.BankingAppSpringBoot.BankingApp.service;

import com.BankingAppSpringBoot.BankingApp.dto.AuthResponse;
import com.BankingAppSpringBoot.BankingApp.dto.LoginRequest;
import com.BankingAppSpringBoot.BankingApp.dto.RegisterRequest;

public interface AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
}
