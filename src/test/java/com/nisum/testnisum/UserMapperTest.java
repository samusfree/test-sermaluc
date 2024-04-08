package com.sermaluc.testbci;

import com.sermaluc.testbci.data.entity.User;
import com.sermaluc.testbci.mappers.UserMapper;
import com.sermaluc.testbci.security.CustomUserDetails;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.sermaluc.testbci.utils.CreateObjectsUtil.getUser;

public class UserMapperTest {
    @Test
    public void testFromUserToCustomDetails() {
        UserMapper userMapper = new UserMapper();
        User user = getUser();
        CustomUserDetails customUserDetails = userMapper.customUserDetailsFromUser(user);
        Assertions.assertEquals(user.getEmail(), customUserDetails.getEmail());
        Assertions.assertEquals(user.getId(), customUserDetails.getId());
        Assertions.assertEquals(user.getEmail(), customUserDetails.getUsername());
        Assertions.assertEquals(user.getPassword(), customUserDetails.getPassword());
        Assertions.assertEquals(user.isActive(), customUserDetails.isEnabled());
    }
}
