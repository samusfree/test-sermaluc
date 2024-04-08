package com.sermaluc.testbci.service;

import com.sermaluc.testbci.data.entity.User;
import com.sermaluc.testbci.data.repository.PhoneRepository;
import com.sermaluc.testbci.data.repository.UserRepository;
import com.sermaluc.testbci.dto.UserDTO;
import com.sermaluc.testbci.dto.UserRequestDTO;
import com.sermaluc.testbci.exception.EmailExistsException;
import com.sermaluc.testbci.mappers.UserMapper;
import com.sermaluc.testbci.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static com.sermaluc.testbci.utils.CreateObjectsUtil.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

public class UserServiceTest {
    private UserRepository userRepository;
    private JWTService jwtService;
    private UserMapper userMapper;
    UserServiceImpl userService;

    @BeforeEach
    void setup() {
        userRepository = Mockito.mock();
        PhoneRepository phoneRepository = Mockito.mock();
        jwtService = Mockito.mock();
        userMapper = new UserMapper();
        userService = new UserServiceImpl(userRepository, phoneRepository, userMapper, jwtService);
    }

    @DisplayName("Create user")
    @Test
    void testCreateUser() {
        UserRequestDTO userRequestDTO = getUserRequestDTO();
        UserDTO expected = getUserDTO();
        Mockito.when(userRepository.findByEmail(userRequestDTO.email())).thenReturn(Optional.empty());
        Mockito.when(userRepository.save(any())).thenReturn(userMapper.fromUserDTO(expected));
        UserDTO response = userService.createUser(userRequestDTO);
        Assertions.assertEquals(expected.id(), response.id());
        Assertions.assertEquals(expected.name(), response.name());
        Assertions.assertEquals(expected.email(), response.email());
        Assertions.assertEquals(expected.phones().size(), 1);
    }

    @DisplayName("Email Already Exists")
    @Test
    void testEmailExists() {
        UserRequestDTO userRequestDTO = getUserRequestDTO();
        User user = userMapper.fromRequestDTO(userRequestDTO);
        Mockito.when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));
        Mockito.when(jwtService.getJWTToken(any())).thenReturn("2323232323232323");
        Exception exception = assertThrows(EmailExistsException.class, () -> userService.createUser(userRequestDTO));
        assertEquals(exception.getMessage(), "The email test@gmail.com is already registered");
    }
}
