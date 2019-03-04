package com.sample.usermanagement.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sample.usermanagement.exception.ServiceException;
import com.sample.usermanagement.service.UserService;
import com.sample.usermanagement.web.model.UserRequestResponse;



@RestController
@RequestMapping("/api/user")
public class UserController {
    
    static final Logger LOG = LoggerFactory.getLogger(UserController.class);
    
    @Autowired
    UserService userService;
    
    @PostMapping
    public ResponseEntity<Object> createUser(@RequestBody @Valid UserRequestResponse userRequest) throws ServiceException {
        try {
            Long userId = userService.saveOrUpdateUser(userRequest);
            if (userId != null) {
                return new ResponseEntity<Object>("User is created successfully with Id "+userId, HttpStatus.CREATED);
            }
            
        } catch (Exception ex) {
            LOG.info("Error while saving user", ex);
            return new ResponseEntity<Object>(null, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Object>(null, HttpStatus.BAD_REQUEST);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Object> getUser(@PathVariable("id") Long id) throws ServiceException {
        UserRequestResponse userResponse = null;
            userResponse = userService.getUser(id);
            if (userResponse == null) {
                return new ResponseEntity<Object>("No Data found for User Id"+id, HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<Object>(userResponse, HttpStatus.OK);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable("id") Long id) throws ServiceException {
            userService.deleteUser(id);
            return new ResponseEntity<Object>("Success", HttpStatus.OK);
    }

}
