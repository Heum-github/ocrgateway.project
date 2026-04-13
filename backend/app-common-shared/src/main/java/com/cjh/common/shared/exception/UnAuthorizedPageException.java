package com.cjh.common.shared.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class UnAuthorizedPageException extends RuntimeException {
    public UnAuthorizedPageException(String message) {
        super(message);
    }
}