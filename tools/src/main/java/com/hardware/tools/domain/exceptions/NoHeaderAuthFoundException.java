package com.hardware.tools.domain.exceptions;

import org.springframework.security.core.AuthenticationException;

public class NoHeaderAuthFoundException extends AuthenticationException {
    public NoHeaderAuthFoundException(String msg) {
        super(msg);
    }
}
