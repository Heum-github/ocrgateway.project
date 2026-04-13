package com.cjh.domain.ocr.shared.exception;

public class AppBaseException extends RuntimeException {

    private final String errorCode;

    public AppBaseException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
