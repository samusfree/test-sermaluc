package com.sermaluc.testbci.mappers;

import com.sermaluc.testbci.data.entity.User;
import com.sermaluc.testbci.security.CustomUserDetails;
import com.sermaluc.testbci.utils.CreateObjectsUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class UserMapperTest {

    @Test
    public void testFromUserToCustomDetails() {
        UserMapper userMapper = new UserMapper();
        User user = CreateObjectsUtil.getUser();
        CustomUserDetails customUserDetails = userMapper.customUserDetailsFromUser(user);
        Assertions.assertEquals(user.getEmail(), customUserDetails.getEmail());
        Assertions.assertEquals(user.getId(), customUserDetails.getId());
        Assertions.assertEquals(user.getEmail(), customUserDetails.getUsername());
        Assertions.assertEquals(user.getPassword(), customUserDetails.getPassword());
        Assertions.assertEquals(user.isActive(), customUserDetails.isEnabled());
    }
}
