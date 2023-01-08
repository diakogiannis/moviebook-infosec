package com.diakogiannis.psarros.propertyregistry.users;

import com.diakogiannis.psarros.propertyregistry.exceptions.UserExistsException;
import com.diakogiannis.psarros.propertyregistry.model.entity.users.Users;
import com.diakogiannis.psarros.propertyregistry.service.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserTests {


    private static Users user1;
    private static Users user2;
    @Autowired
    private UserService userService;

    @Before
    public void init() {
        user1 = new Users(UUID.randomUUID().toString(), "bar", "foo", "bar", true);
        user2 = new Users(UUID.randomUUID().toString(), "bar", "foo", "bar", true);

    }


    @Test(expected = UserExistsException.class)
    public void duplicateUser() throws UserExistsException {
        userService.registerUser(user1);
        userService.registerUser(user1);

    }

    @Test
    public void registerUser() throws UserExistsException {
        userService.registerUser(user2);
        Assert.assertNotNull(userService.findUserByUsername(user2.getUsername()));
    }


}



