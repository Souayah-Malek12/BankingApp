package com.BankingAppSpringBoot.BankingApp.service.serviceImpl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.BankingAppSpringBoot.BankingApp.dto.AuthResponse;
import com.BankingAppSpringBoot.BankingApp.dto.LoginRequest;
import com.BankingAppSpringBoot.BankingApp.dto.RegisterRequest;
import com.BankingAppSpringBoot.BankingApp.entity.User;
import com.BankingAppSpringBoot.BankingApp.exception.BadRequestException;
import com.BankingAppSpringBoot.BankingApp.exception.ResourceNotFoundException;
import com.BankingAppSpringBoot.BankingApp.repository.UserRepository;
import com.BankingAppSpringBoot.BankingApp.security.JwtService;
import com.BankingAppSpringBoot.BankingApp.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final SmsService   smsService;



    public AuthServiceImpl(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            AuthenticationManager authenticationManager,
            SmsService   smsService
            
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.smsService=smsService;
    }

    @Override
    public AuthResponse register(RegisterRequest request) {
        // Check if user already exists
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new BadRequestException("Email already registered"+request.getEmail());
        }

        // Create new user
        User user = new User(
                request.getFullName(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()),
                request.getPhoneNumber(),
                request.getAddress(),
                User.Role.USER
        );

        userRepository.save(user);

        String message = String.format(
    "Dear %s, you have successfully registered with AGV Bank.",
    request.getFullName()
);

smsService.sendSms(request.getPhoneNumber(), message);

    
            smsService.sendSms(request.getPhoneNumber(), message);
    

        // Generate JWT token
        String jwtToken = jwtService.generateToken(user);

        return new AuthResponse(
                jwtToken,
                user.getEmail(),
                user.getFullName(),
                user.getRole().name()
        );
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        // Authenticate user
        try {
                authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                    )
                );
            } catch (BadCredentialsException ex) {
                throw new BadRequestException("Invalid email or password");
            }

        // Find user
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"+request.getEmail()));

        // Generate JWT token
        String jwtToken = jwtService.generateToken(user);

        return new AuthResponse(
                jwtToken,
                user.getEmail(),
                user.getFullName(),
                user.getRole().name()
        );
    }
}
