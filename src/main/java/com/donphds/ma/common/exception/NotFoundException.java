package com.donphds.ma.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;

/**
 * @Author: tplentiful
 * @Since: 1.0
 **/
public class NotFoundException extends HttpClientErrorException {

    public NotFoundException() {
        super(HttpStatus.NOT_FOUND);
    }

    public NotFoundException(String msg) {
        super(HttpStatus.NOT_FOUND, msg);
    }
}
