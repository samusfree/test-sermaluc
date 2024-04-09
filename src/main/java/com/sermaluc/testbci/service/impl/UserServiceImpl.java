package com.sermaluc.testbci.service.impl;

import com.sermaluc.testbci.data.entity.Phone;
import com.sermaluc.testbci.data.entity.User;
import com.sermaluc.testbci.data.repository.PhoneRepository;
import com.sermaluc.testbci.data.repository.UserRepository;
import com.sermaluc.testbci.dto.UserDTO;
import com.sermaluc.testbci.dto.UserRequestDTO;
import com.sermaluc.testbci.exception.BusinessValidationException;
import com.sermaluc.testbci.exception.EmailExistsException;
import com.sermaluc.testbci.mappers.UserMapper;
import com.sermaluc.testbci.service.JWTService;
import com.sermaluc.testbci.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PhoneRepository phoneRepository;
    private final UserMapper userMapper;
    private final JWTService jwtService;
    private final String passwordRegexpValidation;
    private final String passwordExceptionMessage;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PhoneRepository phoneRepository, UserMapper userMapper,
            JWTService jwtService, @Value("${app.validation.password.regexp}") String passwordRegexpValidation,
            @Value("${app.validation.password.message}") String passwordExceptionMessage) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.phoneRepository = phoneRepository;
        this.userMapper = userMapper;
        this.passwordRegexpValidation = passwordRegexpValidation;
        this.passwordExceptionMessage = passwordExceptionMessage;
    }

    @Override
    public UserDTO createUser(UserRequestDTO user) {
        validatePasswordExpresion(user);
        User userEntity = createUserIfNotExists(user);
        List<Phone> phones = user.phones().stream()
                .map(phoneDTO -> userMapper.fromPhoneDTO(userEntity.getId(), phoneDTO)).collect(Collectors.toList());
        phoneRepository.saveAll(phones);
        return userMapper.fromUser(userEntity, phones.stream().map(userMapper::fromPhone).collect(Collectors.toList()));
    }

    private User createUserIfNotExists(UserRequestDTO user) {
        Optional<User> userEntity = userRepository.findByEmail(user.email());

        if (userEntity.isPresent()) {
            throw new EmailExistsException(user.email());
        }

        return userRepository.save(userMapper.fromRequestDTO(user, jwtService.getJWTToken(user)));
    }

    private void validatePasswordExpresion(UserRequestDTO user) {
        Pattern pattern = Pattern.compile(passwordRegexpValidation);
        Matcher matcher = pattern.matcher(user.password());
        if (!matcher.matches()) {
            throw new BusinessValidationException(passwordExceptionMessage);
        }
    }
}
