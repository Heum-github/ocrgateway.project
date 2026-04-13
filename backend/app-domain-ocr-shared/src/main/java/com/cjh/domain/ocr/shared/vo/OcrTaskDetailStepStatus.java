package com.cjh.domain.ocr.shared.vo;

import java.time.LocalDateTime;

import com.cjh.domain.ocr.shared.enums.OcrStepStatus;
import com.cjh.domain.ocr.shared.enums.OcrTaskDetailStep;

import lombok.Getter;

@Getter
public class OcrTaskDetailStepStatus {

    private final OcrTaskDetailStep step;           // 단계
    private final OcrStepStatus status;             // 단계상태 - STARTED, SUCCEEDED, FAILED, SKIPPED
    private final LocalDateTime stepDateTime;       // 단계일시
    private final int httpCode;                     // http 응답코드
    private final String message;                   // 메시지
    private final Object resultObject;

    public OcrTaskDetailStepStatus(OcrTaskDetailStep step, OcrStepStatus status) {
        this(step, status, 0, null, null);
    }

    public OcrTaskDetailStepStatus(OcrTaskDetailStep step, OcrStepStatus status, String message) {
        this(step, status, 0, message, null);
    }

    public OcrTaskDetailStepStatus(OcrTaskDetailStep step, OcrStepStatus status, int httpCode, String message) {
        this(step, status, httpCode, message, null);
    }
 
    public OcrTaskDetailStepStatus(OcrTaskDetailStep step, OcrStepStatus status, int httpCode, String message, Object resultObject) {
        this.step = step;
        this.status = status;
        this.httpCode = httpCode;
        this.message = message;
        this.resultObject = resultObject;
        this.stepDateTime = LocalDateTime.now();
    }
}
