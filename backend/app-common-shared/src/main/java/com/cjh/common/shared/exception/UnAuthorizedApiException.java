package com.cjh.common.shared.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class UnAuthorizedApiException extends RuntimeException {
    public UnAuthorizedApiException(String message) {
        super(message);
    }
}