package com.cjh.domain.ocr.shared.exception;

public class ConvertBase64Exception extends AppBaseException{
    public ConvertBase64Exception() {
        super(AppErrorCode.CONVERT_BASE64_ERROR.getMessage(), AppErrorCode.CONVERT_BASE64_ERROR.getCode());
    }

    public ConvertBase64Exception(AppErrorCode code) {
        super(code.getCode(), code.getMessage());
    }
}
