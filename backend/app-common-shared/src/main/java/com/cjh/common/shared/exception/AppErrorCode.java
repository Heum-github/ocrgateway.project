package com.cjh.common.shared.exception;

public enum AppErrorCode {

    MISSING_FILE_ERROR("400", "파일이 누락되었습니다."),
    INVALID_FILE_EXTENSION_ERROR("400", "잘못된 확장자 파일입니다."),
    IMAGE_NOT_FOUND_ERROR_FROM_LEGACY("500", "이미지 서버에서 데이터를 조회하지 못했습니다."),
    IMAGE_DOWNLOAD_ERROR_FROM_LEGACY("500", "이미지 서버에서 파일을 다운로드하지 못했습니다."),
    IMAGE_UPLOAD_ERROR_TO_LEGACY("500", "이미지 서버에 파일을 업로드하지 못했습니다."),
    INVALID_UPSTAGE_INFERENCE_DOCUMENT_TYPE_RESPONSE_ERROR("500", "유효하지 않은 업스테이지 문서분류 응답입니다."),
    INVALID_UPSTAGE_INFERENCE_DOCUMENT_TYPE_ETC_RESPONSE_ERROR("500", "유효하지 않은 업스테이지 문서분류 응답입니다. ETC 기타로 분류되었습니다"),
    INVALID_UPSTAGE_INFERENCE_RESPONSE_ERROR("500", "유효하지 않은 업스테이지 응답입니다."),
    INVALID_UPSTAGE_INFERENCE_RESPONSE_MISSING_DATA_ERROR("500", "업스테이지 추출결과 오류입니다. 응답 값에 필수 값이 누락되었습니다."),
    LEGACY_LAYOUT_CONVERT_INVALID_PARAMETER_ERROR("500", "레이아웃 변환을 할 수 없습니다 - 유효하지 않은 파라메터 입니다"),
    LEGACY_LAYOUT_CONVERT_NOT_FOUND_FIELD_ERROR("500", "레이아웃 변환을 할 수 없습니다 - 추출 항목을 찾지 못했습니다"),
    LEGACY_LAYOUT_CONVERT_ERROR("500", "레이아웃 변환 중 오류가 발생하였습니다."),
    LEGACY_LAYOUT_CONVERT_MAPPING_ERROR("500", "레이아웃 변환 중 오류가 발생하였습니다 - 일치하는 매핑 코드가 없습니다. 매핑 코드를 확인하세요"),
    SFTP_ERROR("500", "SFTP 처리중 오류가 발생하였습니다."),
    SERVICE_UNAVAILABLE("503", "서비스를 사용할 수 없습니다."),
    DUPLICATE_USER_ERROR("500", "동일한 사용자 아이디가 있습니다."),
    NOT_FOUND_USER_ERROR("500", "변경할 사용자 아이디가 없습니다."),
    LOGIN_ERROR("500", "로그인 처리중 오류가 발생하였습니다."),
    ERROR("500", "처리중 오류가 발생하였습니다."),
    DATA_IN_USE_ASSISTANT_ERROR("400", "삭제할 수 없습니다. 어시스턴트관리에서 사용중인 데이터입니다."),
    DUPLICATE_PROMPTNAME_ERROR("400", "중복된 프롬프트명입니다."),
    LONG_FOR_COLUMN_ERROR("500","컬럼 값의 길이가 초과하였습니다"),
    DUPLICATE_MENU_ERROR("400","중복된 메뉴명입니다." );

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
