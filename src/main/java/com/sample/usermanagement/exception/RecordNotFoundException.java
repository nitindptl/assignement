package com.sample.usermanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NO_CONTENT)
public class RecordNotFoundException extends ServiceException
{

    private static final long serialVersionUID = 5911288133604750906L;

    public RecordNotFoundException(String messgae) {
        super(messgae);
    }
}
