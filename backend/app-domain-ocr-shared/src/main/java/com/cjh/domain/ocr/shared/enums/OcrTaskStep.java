package com.cjh.domain.ocr.shared.enums;

public enum OcrTaskStep {
    OCR_REQUEST_PARSING,                // 요청 전문 파싱
    OCR_RESULT_LAYOUT_CONVERSION,       // OCR 결과 레이아웃 생성
    OCR_FORMCODE_UPDATE,                // 서식코드 업데이트
    OCR_RESULT_SEND                     // 결과 전송
}