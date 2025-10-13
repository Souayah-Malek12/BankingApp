package com.BankingAppSpringBoot.BankingApp.service;

import java.util.List;



import com.BankingAppSpringBoot.BankingApp.dto.UserDto;

public interface UserService  {

    UserDto createUser(UserDto userDto);

     UserDto getUserById(Long Id);
     List<UserDto> getAllUsers();
    UserDto UpdateUser(Long Id, UserDto userDto);
    String DeleteUser(Long id);

}
