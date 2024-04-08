package com.sermaluc.testbci.service;

import com.sermaluc.testbci.dto.UserRequestDTO;

public interface JWTService {
    String getJWTToken(UserRequestDTO user);

    boolean validateAccessToken(String token);

    String getSubject(String token);
}
