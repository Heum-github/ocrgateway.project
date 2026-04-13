package com.cjh.common.shared.exception;

public class DuplicateUserIdException extends AppBaseException{

    public DuplicateUserIdException() {
        super(AppErrorCode.DUPLICATE_USER_ERROR.getCode(), AppErrorCode.DUPLICATE_USER_ERROR.getMessage());
    }

    public DuplicateUserIdException(AppErrorCode code) {
        super(code.getCode(), code.getMessage());
    }
}