package com.sample.usermanagement.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.sample.usermanagement.web.model.UserRequestResponse;

@Entity
@Table(name = "USER", 
uniqueConstraints = @UniqueConstraint(name = "user_name_uc"
                                      ,columnNames = "USER_NAME"))
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(name="USER_NAME")
    private String userName;
    @Column(name="PASSWORD")
    private String password;
    @Column(name="STATUS")
    private String status;
    
    
    public User() {
        super();
    }
    
    public UserRequestResponse toRequestResponse() {
        UserRequestResponse rr = new UserRequestResponse();
        rr.setId(this.id);
        rr.setUserName(this.userName);
        rr.setPassword(this.password);
        rr.setStatus(this.status);
        return rr;
    }
    
    public User toUser(UserRequestResponse userRR) {
        User user = new User();
        user.setId(userRR.getId());
        user.setUserName(userRR.getUserName());
        user.setPassword(userRR.getPassword());
        user.setStatus(userRR.getStatus());
        return user;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}
