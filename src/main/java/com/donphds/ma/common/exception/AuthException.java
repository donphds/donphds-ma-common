package com.donphds.ma.common.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;

import java.nio.charset.Charset;

/**
 * @Author: tplentiful
 * @Since: 1.0
 **/
public class AuthException extends HttpClientErrorException {
    public AuthException(String msg) {
        super(HttpStatus.UNAUTHORIZED, msg);
    }
    public AuthException(String msg, HttpHeaders headers, byte[] body) {
        super(HttpStatus.UNAUTHORIZED, msg, headers, body, Charset.defaultCharset());
    }
}
