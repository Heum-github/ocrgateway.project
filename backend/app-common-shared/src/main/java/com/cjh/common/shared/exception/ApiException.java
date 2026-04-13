package com.cjh.common.shared.exception;

public class ApiException extends AppBaseException {

    public ApiException(String message) {
        super("500", message);
    }

    public ApiException(AppErrorCode code) {
        super(code.getCode(), code.getMessage());
    }

}


