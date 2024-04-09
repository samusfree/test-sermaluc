package com.sermaluc.testbci.utils;

import com.sermaluc.testbci.data.entity.User;
import com.sermaluc.testbci.dto.PhoneDTO;
import com.sermaluc.testbci.dto.UserDTO;
import com.sermaluc.testbci.dto.UserRequestDTO;
import com.sermaluc.testbci.mappers.UserMapper;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class CreateObjectsUtil {
    public static String SECRET_KEY = "abcdefghijklmnOPQRSTUVWXYZabcdefghijklmnOPQRSTUVWXYZabcdefghijklmnOPQRSTUVWXYZ";

    public static UserRequestDTO getUserRequestDTO() {
        return new UserRequestDTO("test", "test@gmail.com", "Samus123", List.of(getPhoneDTO()));
    }

    public static UserRequestDTO getUserRequestDTOWithWrongPassword() {
        return new UserRequestDTO("test", "test@gmail.com", "31", List.of(getPhoneDTO()));
    }

    public static UserDTO getUserDTO() {
        return new UserDTO(UUID.randomUUID(), "test", "test@gmail.com", "Samus123", LocalDate.now(),
                null, LocalDate.now(), "ddsddsdsdsd", true, List.of(getPhoneDTO()));
    }

    public static User getUser() {
        return new UserMapper().fromUserDTO(getUserDTO());
    }

    public static PhoneDTO getPhoneDTO() {
        return new PhoneDTO("55555", "55", "51");
    }

    private CreateObjectsUtil() {
    }
}
