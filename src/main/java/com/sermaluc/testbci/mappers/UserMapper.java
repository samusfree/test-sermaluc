package com.sermaluc.testbci.mappers;

import com.sermaluc.testbci.data.entity.Phone;
import com.sermaluc.testbci.data.entity.User;
import com.sermaluc.testbci.dto.PhoneDTO;
import com.sermaluc.testbci.dto.UserDTO;
import com.sermaluc.testbci.dto.UserRequestDTO;
import com.sermaluc.testbci.security.CustomUserDetails;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Component
public class UserMapper {
    public User fromRequestDTO(UserRequestDTO userRequestDTO) {
        return fromRequestDTO(userRequestDTO, null);
    }

    public User fromRequestDTO(UserRequestDTO userRequestDTO, String token) {
        return new User(null, userRequestDTO.name(), userRequestDTO.email(), userRequestDTO.password(),
                LocalDate.now(), null, LocalDate.now(), token, true);
    }

    public Phone fromPhoneDTO(UUID id, PhoneDTO phoneDTO) {
        return new Phone(id, null, phoneDTO.number(), phoneDTO.cityCode(), phoneDTO.countryCode());
    }

    public PhoneDTO fromPhone(Phone phone) {
        return new PhoneDTO(phone.getNumber(), phone.getCityCode(), phone.getCountryCode());
    }

    public UserDTO fromUser(User user, List<PhoneDTO> phones) {
        return new UserDTO(user.getId(), user.getName(), user.getEmail(), user.getPassword(), user.getCreated(), user.getModified(),
                user.getLastLogin(), user.getToken(), user.isActive(), phones);
    }

    public User fromUserDTO(UserDTO userDTO) {
        return new User(userDTO.id(), userDTO.name(), userDTO.email(), userDTO.password(), userDTO.created(), userDTO.modified(),
                userDTO.lastLogin(), userDTO.token(), userDTO.active());
    }


    public CustomUserDetails customUserDetailsFromUser(User user) {
        return CustomUserDetails.builder().id(user.getId()).email(user.getEmail()).password(user.getPassword()).active(user.isActive()).build();
    }
}
