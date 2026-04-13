package com.cjh.domain.ocr.shared.exception;

public enum AppErrorCode {
    
    PARAMETER_MISSING_FILE("400", "파일이 누락되었습니다."),
    INVALID_FILE_EXTENSION("400", "잘못된 확장자 파일입니다."),
    ECM_SOCKET_CONNECTION_ERROR("500", "ECM 소켓 통신 중 오류가 발생하였습니다."),
    IMAGE_NOT_FOUND_ERROR_FROM_LEGACY("500", "이미지 서버에서 데이터를 조회하지 못했습니다."),
    IMAGE_DOWNLOAD_ERROR_FROM_LEGACY("500", "이미지 서버에서 파일을 다운로드하지 못했습니다."),
    IMAGE_UNZIP_ERROR("500", "이미지 파일 압축 해제 중 오류가 발생하였습니다."),
    IMAGE_DECRYPT_ERROR("500", "이미지 파일 복호화 중 오류가 발생하였습니다."),
    INVALID_UPSTAGE_INFERENCE_RESPONSE_ERROR("500", "유효하지 않은 업스테이지 추론 결과 응답입니다."),
    CONVERT_COMPASS_LAYOUT_ERROR("500", "레이아웃 변환 중 오류가 발생하였습니다."),
    CONVERT_BASE64_ERROR("500", "이미지 BASE64 변환 중 오류가 발생하였습니다."),
    ONTOLOGIES_NOT_FOUND_ERROR("500", "온톨로지 정보를 찾을 수 없습니다."),
    INVALID_UPSTAGE_INFERENCE_DOCUMENT_TYPE_RESPONSE_ERROR("500", "유효하지 않은 업스테이지 문서분류 응답입니다."),
    INVALID_UPSTAGE_INFERENCE_DOCUMENT_TYPE_ETC_RESPONSE_ERROR("500", "유효하지 않은 업스테이지 문서분류 응답입니다. ETC 기타로 분류되었습니다"),
    INVALID_VALIDATOR_LAYOUT_ERROR("500", "Validator 응답 레이아웃 필수값이 누락되었습니다."),
    SERVICE_UNAVAILABLE("503", "서비스를 사용할 수 없습니다.");
    
    private final String code;
    private final String message;

    AppErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
