package com.cjh.common.shared.exception;

public class NotFoundUserException extends AppBaseException{

    public NotFoundUserException() {
        super(AppErrorCode.NOT_FOUND_USER_ERROR.getCode(), AppErrorCode.NOT_FOUND_USER_ERROR.getMessage());
    }

    public NotFoundUserException(AppErrorCode code) {
        super(code.getCode(), code.getMessage());
    }
}