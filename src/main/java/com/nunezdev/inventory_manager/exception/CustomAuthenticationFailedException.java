package com.nunezdev.inventory_manager.exception;

import org.springframework.security.core.AuthenticationException;

public class CustomAuthenticationFailedException extends AuthenticationException {

    public CustomAuthenticationFailedException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public CustomAuthenticationFailedException(String msg) {
        super(msg);
    }
}
