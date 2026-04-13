package com.cjh.common.shared.exception;

public class AppException extends AppBaseException{

    public AppException() {
        super(AppErrorCode.ERROR.getCode(), AppErrorCode.ERROR.getMessage());
    }

    public AppException(AppErrorCode code) {
        super(code.getCode(), code.getMessage());
    }
}
