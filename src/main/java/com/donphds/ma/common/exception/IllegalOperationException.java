package com.donphds.ma.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import static org.springframework.http.HttpStatus.PRECONDITION_REQUIRED;

/**
 * @Author: donphds
 * @Since: 1.0
 **/
public class IllegalOperationException extends HttpClientErrorException {
    public IllegalOperationException(String msg) {
        super(PRECONDITION_REQUIRED, msg);
    }
}
