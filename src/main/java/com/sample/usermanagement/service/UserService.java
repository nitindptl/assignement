package com.sample.usermanagement.service;

import org.springframework.security.access.annotation.Secured;

import com.sample.usermanagement.exception.ServiceException;
import com.sample.usermanagement.web.model.UserRequestResponse;


/** 
 * @author nitin
 *
 */
public interface UserService {

    
    @Secured("ROLE_ADMIN")
    public Long saveOrUpdateUser(UserRequestResponse userRequest) throws ServiceException;
    
    public UserRequestResponse getUser(Long id) throws ServiceException;
    
    @Secured("ROLE_ADMIN")
    public boolean deleteUser(Long id) throws ServiceException;
}
