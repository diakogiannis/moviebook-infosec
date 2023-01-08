package com.diakogiannis.psarros.propertyregistry;

import com.diakogiannis.psarros.propertyregistry.model.entity.movies.Movie;
import com.diakogiannis.psarros.propertyregistry.model.entity.users.Users;
import com.diakogiannis.psarros.propertyregistry.service.MovieService;
import com.diakogiannis.psarros.propertyregistry.service.UserService;
import com.diakogiannis.psarros.propertyregistry.util.PasswordUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.Set;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class PropertyRegistryApplicationTests {

    private static Users user1;
    private static Movie movie1;
    @Autowired
    MovieService movieService;
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordUtils passwordUtils;

    @Before
    public void init() {

        user1 = new Users("foo", "bar", "foo", "bar", true);


    }


    @WithMockUser("sa")
    @Test
    public void find_authorised_404() throws Exception {

        mockMvc.perform(get("/something-possibly-secured-that-does-not-exists"))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void find_unauthorised() throws Exception {

        mockMvc.perform(get("/something-possibly-secured"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login/**"));
    }

    //Test the user can login after registration
    @Test
    public void registerUser() throws Exception {
        Users users = userService.registerUser(user1);
        mockMvc.perform(get("/something-possibly-secured").with(httpBasic(users.getUsername(), users.getPassword())));
    }

    //Test passwords are loaded
    @Test
    public void loadPasswords(){
        Set<String> passwords = passwordUtils.getPasswords();
        Assert.assertTrue(passwords.size() > 1);
    }

}
