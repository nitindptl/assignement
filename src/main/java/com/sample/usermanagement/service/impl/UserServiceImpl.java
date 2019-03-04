package com.sample.usermanagement.service.impl;

import java.util.Optional;

import com.sample.usermanagement.constant.Status;
import com.sample.usermanagement.entity.User;
import com.sample.usermanagement.exception.RecordNotFoundException;
import com.sample.usermanagement.exception.ServiceException;
import com.sample.usermanagement.repository.UserRepository;
import com.sample.usermanagement.service.UserService;
import com.sample.usermanagement.web.model.UserRequestResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * UserService implementation class.
 *  CRUD Operation on user entity.
 * 
 * @author nitin
 *
 */
@Service
@CacheConfig(cacheNames = "users")
public class UserServiceImpl implements UserService {

    static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);
    
    @Autowired
    UserRepository userRepo;
    
    /* This method is to save or update user
     * @param UserRequest
     * @return userId
     */
    @Transactional
    public Long saveOrUpdateUser(UserRequestResponse userRequest) throws ServiceException {
        LOG.info("saving User: " + userRequest);
        User user = null;
        try {
            //New User
            if(userRequest.getId() == null) {
                userRequest.setStatus(Status.DEACTIVATED.name());
            }
            user = new User().toUser(userRequest);
            user = userRepo.save(user);
            return user.getId();
        } catch (Exception ex) {
            LOG.info("Error saving User: " + userRequest, ex);
            throw new ServiceException(ex);
        }
    }

    /* This method is to get user detail using user Id
     * @param id
     * @return UserRequestResponse
     */
    @Override
    @Cacheable()
    @Transactional
    public UserRequestResponse getUser(Long id) throws ServiceException {
        LOG.info("fetching User by Id: " + id);
        UserRequestResponse userRR = null;
        Optional<User> user = userRepo.findById(id);
        if (user.isPresent()) {
            userRR = user.get().toRequestResponse();
        } else {
            throw new RecordNotFoundException("Invalid user id : " + id);
        }
        return userRR;
    }

    /* This method is to delete user using Id
     * @param user id
     * 
     */
    @Override
    @Transactional
    public boolean deleteUser(Long id) throws ServiceException {
        LOG.info("deleteing User by Id: " + id);
        Optional<User> user = userRepo.findById(id);
        boolean isDeleted = false;
        if (user.isPresent()) {
            userRepo.deleteById(id);
            isDeleted = true;
        } else {
            throw new RecordNotFoundException("Invalid user id : " + id);
        }
        return isDeleted;
    }

}
