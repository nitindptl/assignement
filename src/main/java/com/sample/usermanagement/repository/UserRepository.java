package com.sample.usermanagement.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sample.usermanagement.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long>{

}
