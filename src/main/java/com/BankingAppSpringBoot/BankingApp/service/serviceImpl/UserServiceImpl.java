package com.BankingAppSpringBoot.BankingApp.service.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.BankingAppSpringBoot.BankingApp.dto.UserDto;
import com.BankingAppSpringBoot.BankingApp.entity.User;
import com.BankingAppSpringBoot.BankingApp.exception.ResourceNotFoundException;
import com.BankingAppSpringBoot.BankingApp.mapper.UserMapper;
import com.BankingAppSpringBoot.BankingApp.repository.UserRepository;
import com.BankingAppSpringBoot.BankingApp.service.UserService;

import com.BankingAppSpringBoot.BankingApp.exception.BadRequestException; // ✅ CORRECT

import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder; // add this

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new BadRequestException("Email already in use: " + userDto.getEmail());
        }
        User user = userMapper.toEntity(userDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        try{
        userRepository.save(user);
        }catch (Exception e){
            throw new BadRequestException("Failed to create user: " + e.getMessage());

        }
        return userMapper.toDto(user);
    }

    @Override
    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
        return userMapper.toDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<UserDto> users = userRepository.findAll()
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
        if(users.isEmpty()){
            throw new ResourceNotFoundException("No users found");
        }
        return users;
    }

    @Override
    public UserDto UpdateUser(Long id, UserDto userDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new  ResourceNotFoundException("User not found: " + id));

        if(userDto.getFullName() != null){
        user.setFullName(userDto.getFullName());
        }
        if (userDto.getEmail() != null) {
            user.setEmail(userDto.getEmail());
        }
        if (userDto.getPassword() != null) {
            user.setPassword(userDto.getPassword());
        }
        if (userDto.getPhoneNumber() != null) {
            user.setPhoneNumber(userDto.getPhoneNumber());
        }
        if (userDto.getAddress() != null) {
            user.setAddress(userDto.getAddress());
        }
        if (userDto.getRole() != null) {
            user.setRole(userDto.getRole());
        }

        User updatedUser;
        try{
         updatedUser = userRepository.save(user);
        }catch(Exception e){
            throw new ResourceNotFoundException("No users found");
        }
        return userMapper.toDto(updatedUser);
    }

    @Override
    public String DeleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + id));
        userRepository.delete(user);

        String deletedUser = user.getEmail()+"  deleted Succesffully";

        try {
            userRepository.delete(user);
        } catch (Exception e) {
            throw new BadRequestException("Failed to delete user: " + e.getMessage());
        }

        return deletedUser;
    }
}
