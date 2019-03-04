package com.sample.usermanagement.test.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import javax.transaction.Transactional;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sample.usermanagement.exception.RecordNotFoundException;
import com.sample.usermanagement.service.UserService;
import com.sample.usermanagement.web.model.UserRequestResponse;

@SpringBootTest
@RunWith(SpringRunner.class)
@WithMockUser(value = "test", roles = "ADMIN")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Transactional
public class UserControllerTest extends BaseControllerTest{

    @MockBean
    UserService userService;
    
    @Test
    public void test_AUserCreation_Success() {
        LOG.info("Test User creation :: ");
        MvcResult fetchResult;
        String request=null;
        try {
            UserRequestResponse user = new UserRequestResponse();
            user.setUserName("Test1");
            user.setPassword("password");
            request = new ObjectMapper().writeValueAsString(user);
            fetchResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/user")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(request))
                    .andExpect(status().isCreated())
                    .andReturn();
            LOG.info("test_AUserCreation_Success response:: " + fetchResult.getResponse().getContentAsString());
        } catch (Exception e) {
            LOG.info("test_AUserCreation_Success Error:: "+request);
        }
    }
    
    @Test
    public void test_BUserCreation_Failure() {
        MvcResult fetchResult;
        try {
            fetchResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/user")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{}"))
                    .andExpect(status().isBadRequest())
                    .andReturn();
            LOG.info("test_BUserCreation_Failure response:: " + fetchResult.getResponse().getContentAsString());
        } catch (Exception e) {
            LOG.info("test_BUserCreation_Failure Error:: ");
        }
    }
    
    @Test
    public void test_CGetUser_Success() {
        MvcResult fetchResult;
        try {
            UserRequestResponse user = new UserRequestResponse();
            user.setId(1L);
            user.setUserName("Test1");
            user.setPassword("password");
            Mockito.when(
                    userService.getUser(1L)).thenReturn(user);
            fetchResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/user/1")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id", equalTo(1)))
                    .andReturn();
            LOG.info("test_CGetUser_Success response:: " + fetchResult.getResponse().getContentAsString());
        } catch (Exception e) {
            LOG.info("test_CGetUser_Success Error:: ");
        }
    }
    
    @Test
    public void test_DGetUser_Failure() {
        MvcResult fetchResult;
        try {
            Mockito.when(
                    userService.getUser(2L)).thenReturn(null);
            fetchResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/user/2")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNoContent())
                    .andReturn();
            LOG.info("test_DGetUser_Failure Response:: " + fetchResult.getResponse().getContentAsString());
        } catch (Exception e) {
            LOG.info("test_DGetUser_Failure Error:: ");
        }
    }

    @Test
    public void test_EDeleteUser_Success() {
        MvcResult fetchResult;
        try {
            fetchResult = mockMvc.perform(MockMvcRequestBuilders.delete("/api/user/1")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andReturn();
            LOG.info("test_EDeleteUser_Success response:: " + fetchResult.getResponse().getContentAsString());
        } catch (Exception e) {
            LOG.info("test_EDeleteUser_Success Error:: ");
        }
    }
    
    @Test
    public void test_FDeletetUser_Failure() {
        MvcResult fetchResult;
        try {
            Mockito.when(
                    userService.deleteUser(2L)).thenThrow(RecordNotFoundException.class);
            fetchResult = mockMvc.perform(MockMvcRequestBuilders.delete("/api/user/2")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNoContent())
                    .andReturn();
            LOG.info("test_FDeletetUser_Failure response:: " + fetchResult.getResponse().getContentAsString());
        } catch (Exception e) {
            LOG.info("test_DGetUser_Failure Error:: ");
        }
    }
}
