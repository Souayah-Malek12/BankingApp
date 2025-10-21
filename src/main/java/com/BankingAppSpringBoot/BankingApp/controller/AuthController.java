package com.BankingAppSpringBoot.BankingApp.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.BankingAppSpringBoot.BankingApp.dto.AuthResponse;
import com.BankingAppSpringBoot.BankingApp.dto.LoginRequest;
import com.BankingAppSpringBoot.BankingApp.dto.RegisterRequest;
import com.BankingAppSpringBoot.BankingApp.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        AuthResponse response = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
            AuthResponse response = authService.login(request);
           
            
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
       
    }
}
