package com.cjh.common.shared.exception;

public class LoginException extends AppBaseException{

    public LoginException() {
        super(AppErrorCode.LOGIN_ERROR.getCode(), AppErrorCode.LOGIN_ERROR.getMessage());
    }

    public LoginException(AppErrorCode code) {
        super(code.getCode(), code.getMessage());
    }
}