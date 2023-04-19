package com.example.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @Author: jason
 * @Description:
 * @Date Create in 2021/9/3 20:50
 */
public class NotLoginException extends AuthenticationException {

    public NotLoginException(String msg, Throwable t) {
        super(msg, t);
    }

    public NotLoginException(String msg) {
        super(msg);
    }
}
