package com.cjh.common.shared.exception;

public class DuplicateCodeException extends AppBaseException{

    public DuplicateCodeException(String customMessage) {
        super("500", customMessage);
    }

    public DuplicateCodeException(AppErrorCode code) {
        super(code.getCode(), code.getMessage());
    }
}