package com.donphds.ma.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpServerErrorException;

/**
 * @Author: tplentiful
 * @Since: 1.0
 **/
public class InternalServerException extends HttpServerErrorException {

    public InternalServerException(String msg) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, msg);
    }
}
