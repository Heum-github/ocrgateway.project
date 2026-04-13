package com.cjh.domain.ocr.shared.enums;

public enum OcrTaskDetailStep {
    // 이미지 다운로드/ 전처리
    ECM_CONNECT,             // ECM 연결
    IMAGE_FILE_DOWNLOAD,     // 이미지 파일 다운로드
    ECM_DISCONNECT,          // ECM 연결 해제
    IMAGE_FILE_DECRYPT,      // 이미지 복호화
    IMAGE_FILE_UNZIP,        // 이미지 압축 해제
    IMAGE_FILE_EXTENSION_CHECK, // 이미지 확장자 확인
    IMAGE_FILE_EXTENSION_VAIDATION, // 이미지 확장자 유효성 검증

    OCR_CLASSIFY,            // 문서분류
    OCR_INFERENCE,           // 정보추출

}