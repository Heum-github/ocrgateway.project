package com.cjh.domain.ocr.shared.enums;

public enum OcrProcessErrorCode {
    OGW_IO("OGWIOE", "게이트웨이 I/O Error"),
    OGW_OOM("OGWOOM", "게이트웨이 OOM"),
    OGW_UNKNOWN("OGWUKN", "게이트웨이 미분류 에러"),
    INBOUND_PARSING_ERROR("E110001", "인바운드 전문 파싱 오류"),
    OGW_THREAD_ERROR("E190001", "게이트웨이 스레드 오류"),
    OGW_SYS_ERROR("E190002", "게이트웨이 시스템 오류"),

    IMG_EXTENSION_VALIDATION_ERROR("910001", "이미지 확장자 유효성 오류"),
    IMG_ECM_CONNECT_ERROR("990001", "ECM connect 오류"),
    IMG_FILE_DOWNLOAD_ERROR("990002", "이미지 파일 다운로드 오류"),
    IMG_ECM_DISCONNECT_ERROR("990003", "ECM disconnect 오류"),
    IMG_FILE_DECRYPT_ERROR("990004", "이미지 복호화 오류"),
    IMG_FILE_UNZIP_ERROR("990005", "이미지 압축해제 오류"),
    IMG_EXTENSION_CHECK_EROR("990006", "이미지 실제 확장자 확인 시 오류"),

    CLASSIFICATION_CLIENT("CLSCLT", "문서분류 게이트웨이 오류"),
    CLASSIFICATION_SERVER("CLSSVR", "문서분류 엔진 오류"),
    CLASSIFICATION_PARSING("CLSPAR", "문서분류 결과 파싱 오류"),

    INFERENCE_CLIENT("INFCLT", "정보추출 게이트웨이 오류"),
    INFERENCE_SERVER("INFSVR", "정보추출 엔진 오류"),
    INFERENCE_PARSING("INFPAR", "정보추출 결과 파싱 오류");

    private final String code;
    private final String description;

    OcrProcessErrorCode(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String code() {
        return code;
    }

    public String description() {
        return description;
    }
}
