package com.sample.usermanagement.test.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.sample.usermanagement.service.UserService;
import com.sample.usermanagement.web.model.UserRequestResponse;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTest {

    @MockBean
    UserRequestResponse userRequestReponse;
    
    @MockBean
    UserService userService;
    
    @Before
    public void setup() {
       
        userRequestReponse.setId(1L);
        userRequestReponse.setUserName("Test");
        userRequestReponse.setPassword("password");
    }

    @Test
    public void test_createUser_success() throws Exception {

        Mockito.when(
                userService.saveOrUpdateUser(Mockito.any())).thenReturn(12L);
        Long id = userService.saveOrUpdateUser(userRequestReponse);
        assertNotNull(id);
    }
    
    @Test
    public void test_createUser_fail() throws Exception {

        Mockito.when(
                userService.saveOrUpdateUser(Mockito.any())).thenReturn(null);
        Long id = userService.saveOrUpdateUser(userRequestReponse);
        assertNull(id);
    }
    
    @Test
    public void test_getUser_success() throws Exception {

        Mockito.when(
                userService.getUser(Mockito.any())).thenReturn(userRequestReponse);
        UserRequestResponse userReponse = userService.getUser(1L);
        assertNotNull(userReponse);
    }
    
    @Test
    public void test_getUser_fail() throws Exception {

        Mockito.when(
                userService.getUser(Mockito.any())).thenReturn(null);
        UserRequestResponse userReponse = userService.getUser(1L);
        assertNull(userReponse);
    }
}
