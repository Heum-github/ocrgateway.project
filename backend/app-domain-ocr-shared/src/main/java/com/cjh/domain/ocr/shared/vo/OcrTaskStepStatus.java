package com.cjh.domain.ocr.shared.vo;

import java.time.LocalDateTime;

import com.cjh.domain.ocr.shared.enums.OcrStepStatus;
import com.cjh.domain.ocr.shared.enums.OcrTaskStep;

public class OcrTaskStepStatus {

    private final OcrTaskStep step;             // 단계
    private final OcrStepStatus status;         // 단계상태 - STARTED, SUCCEEDED, FAILED, SKIPPED
    private final LocalDateTime stepDateTime;   // 단계일시
    private final int httpCode;                 // http 응답코드
    private final String message;               // 메시지
    private final Object resultObject;

    public OcrTaskStepStatus(OcrTaskStep step, OcrStepStatus status, String message) {
        this(step, status, 0, message, null);
    }

    public OcrTaskStepStatus(OcrTaskStep step, OcrStepStatus status, int httpCode, String message) {
        this(step, status, httpCode, message, null);
    }
 
    public OcrTaskStepStatus(OcrTaskStep step, OcrStepStatus status, int httpCode, String message, Object resultObject) {
        this.step = step;
        this.status = status;
        this.httpCode = httpCode;
        this.message = message;
        this.resultObject = resultObject;
        this.stepDateTime = LocalDateTime.now();
    }
}
