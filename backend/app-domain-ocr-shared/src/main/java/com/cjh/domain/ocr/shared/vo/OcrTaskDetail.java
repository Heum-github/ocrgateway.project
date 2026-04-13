package com.cjh.domain.ocr.shared.vo;

import java.util.ArrayList;
import java.util.List;

import com.cjh.domain.ocr.shared.constants.OcrTypeConstants;
import com.cjh.domain.ocr.shared.enums.OcrProcessErrorCode;
import com.cjh.domain.ocr.shared.enums.OcrStepStatus;
import com.cjh.domain.ocr.shared.enums.OcrTaskDetailStep;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OcrTaskDetail {

    // 파일정보
    private OcrTaskTargetFile targetFile;

    // 문서타입 - OCR 결과 문서타입
    private String ocrDocumentType;
    // 문서코드
    private String ocrDocumentCode;

    // 오류코드
    private String errorCode;
    // 오류메시지
    private String errorMessage;

    // 정보추출OCR시작시간
    private String extractionStartDateTime;
    // 정보추출OCR결과
    private String extractionJson;

    private OcrTaskDetailStep step;
    private final List<OcrTaskDetailStepStatus> stepStatusList = new ArrayList<>();

    public OcrTaskDetail() {
    }
    
    public OcrTaskDetail(OcrTaskTargetFile targetFile) {
        this.targetFile = targetFile;
    }

    public void beginStep(OcrTaskDetailStep step) {
        this.step = step;
        stepStatusList.add(new OcrTaskDetailStepStatus(step, OcrStepStatus.STARTED, ""));
    }

    public void completeStep(OcrTaskDetailStep step) {
        completeStep(step, "");
    }

    public void completeStep(OcrTaskDetailStep step, String message) {
        this.step = step;
        stepStatusList.add(new OcrTaskDetailStepStatus(step, OcrStepStatus.SUCCEEDED, message));
    }

    public void failStep(OcrTaskDetailStep step, String message) {
        this.step = step;
        stepStatusList.add(new OcrTaskDetailStepStatus(step, OcrStepStatus.FAILED, message));
    }

    public void failStep(OcrTaskDetailStep step, int httpCode, OcrProcessErrorCode errorCode) {
        this.step = step;
        stepStatusList.add(new OcrTaskDetailStepStatus(step, OcrStepStatus.FAILED, httpCode, errorCode.description()));
        setError(errorCode.code(), errorCode.description());
    }
    
    public void failStep(OcrTaskDetailStep step, int httpCode, OcrProcessErrorCode errorCode, String customMessage) {
        this.step = step;
        stepStatusList.add(
            new OcrTaskDetailStepStatus(step, OcrStepStatus.FAILED, httpCode, customMessage)
        );
        setError(errorCode.code(), customMessage);
    }

    public void skipStep(OcrTaskDetailStep step) {
        this.step = step;
        stepStatusList.add(new OcrTaskDetailStepStatus(step, OcrStepStatus.SKIPPED));
    }

    public boolean isStepSucceeded(OcrTaskDetailStep step) {
        return stepStatusList.stream()
                .anyMatch(s -> s.getStep() == step && s.getStatus() == (OcrStepStatus.SUCCEEDED));
    }

    public boolean isStepFailed(OcrTaskDetailStep step) {
        return stepStatusList.stream()
                .anyMatch(s -> s.getStep() == step && s.getStatus() == (OcrStepStatus.FAILED));
    }
    
    public void setError(OcrProcessErrorCode error) {
        this.errorCode = error.code();
        this.errorMessage = error.description();
    }

    public void setError(String code, String message) {
        this.errorCode = code;
        this.errorMessage = message;
    }

    public void setOcrDocument(String ocrDocCode, String ocrDocType) {
        this.ocrDocumentCode = (ocrDocCode == null) ? OcrTypeConstants.ETC_CODE : ocrDocCode;
        this.ocrDocumentType = ocrDocType;
    }

    // 이미지 다운로드 시작
    // 이미지 다운로드 완료
    // 이미지 다운로드 실패
    // 이미지 복호화 시작
    // 이미지 복호화 완료
    // 이미지 복호화 실패
    // 이미지 압축 해제 시작
    // 이미지 압축 해제 완료
    // 이미지 압축 해제 실패
}