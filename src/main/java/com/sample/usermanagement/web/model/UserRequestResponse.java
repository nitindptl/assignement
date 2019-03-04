package com.sample.usermanagement.web.model;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class UserRequestResponse implements Serializable{

    private static final long serialVersionUID = -1042279915911222024L;
    
    private Long id;
    @NotNull(message = "user name must not be empty")
    private String userName;
    @NotNull(message = "password must not be empty")
    private String password;
    private String status;

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

    @Override
    public String toString() {
        return "UserRequestResponse [id=" + id + ", userName=" + userName + ", password=" + password + ", status=" + status + "]";
    }
}
