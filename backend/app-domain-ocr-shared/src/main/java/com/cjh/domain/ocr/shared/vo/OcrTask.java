package com.cjh.domain.ocr.shared.vo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.cjh.domain.ocr.shared.enums.OcrProcessErrorCode;
import com.cjh.domain.ocr.shared.enums.OcrStepStatus;
import com.cjh.domain.ocr.shared.enums.OcrTaskStep;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OcrTask {
    // ocrTask 고유값
    private String taskId;
    // 처리시작시간
    private String taskStartTime;
    // 처리종료시간
    private String taskEndTime;
    // 처리성공여부
    private boolean success;
    // 결과코드
    private String resultCode;
    // 결과메시지
    private String resultMessage;
    // 오류코드
    private String errorCode;
    // 오류메시지
    private String errorMessage;
    
    // 서식코드 업데이트 레이아웃
    private byte[] documentCodeUpdateLayout;
    // EAI 전송 레이아웃
    private byte[] eaiLayout;

    private OcrTaskStep step;
    private final List<OcrTaskStepStatus> stepStatusList = new ArrayList<>();

    // 처리상세
    private List<OcrTaskDetail> taskDetails = new ArrayList<>();
    // 이미지 파일 정보
    private List<OcrTaskTargetFile> targetFiles = new ArrayList<>();

    public void beginTask() {
        this.taskStartTime = now();
    }

    public void completeTask() {
        success = true;
        this.taskEndTime = now();
    }

    public void failTask(String message) {
        this.errorMessage = message;
        failTask();
    }

    public void failTask() {
        success = false;
        this.taskEndTime = now();
    }

    public void beginStep(OcrTaskStep step) {
        this.step = step;
        stepStatusList.add(new OcrTaskStepStatus(step, OcrStepStatus.STARTED, ""));
    }

    public void completeStep(OcrTaskStep step) {
        completeStep(step, "");
    }

    public void completeStep(OcrTaskStep step, String message) {
        this.step = step;
        this.success = true;
        stepStatusList.add(new OcrTaskStepStatus(step, OcrStepStatus.SUCCEEDED, message));
    }

    public void failStep(OcrTaskStep step, String message) {
        this.step = step;
        this.success = false;
        stepStatusList.add(new OcrTaskStepStatus(step, OcrStepStatus.FAILED, message));
    }

    public void failStep(OcrTaskStep step, String code, String message) {
        this.step = step;
        this.success = false;
        stepStatusList.add(new OcrTaskStepStatus(step, OcrStepStatus.FAILED, message));
    }

    public void setError(OcrProcessErrorCode error) {
        this.errorCode = error.code();
        this.errorMessage = error.description();
    }

    public void setError(String code, String message) {
        this.errorCode = code;
        this.errorMessage = message;
    }

    private String now() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
    }
}
