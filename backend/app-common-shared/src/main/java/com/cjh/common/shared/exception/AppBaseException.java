package com.cjh.common.shared.exception;

public class AppBaseException extends RuntimeException {

    private final String errorCode;
    private final String defaultErrorCode = "500";

    public AppBaseException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public AppBaseException(String message) {
        super(message);
        this.errorCode = defaultErrorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
