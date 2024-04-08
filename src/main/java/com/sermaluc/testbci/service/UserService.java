package com.sermaluc.testbci.service;


import com.sermaluc.testbci.dto.UserDTO;
import com.sermaluc.testbci.dto.UserRequestDTO;

public interface UserService {
    UserDTO createUser(UserRequestDTO user);
}
