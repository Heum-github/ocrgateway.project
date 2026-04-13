package com.cjh.domain.ocr.shared.enums;
import lombok.AllArgsConstructor;
import lombok.Getter;
 
@Getter
@AllArgsConstructor
public enum OcrStepStatus {

    STARTED( "STA"),
    SUCCEEDED( "SUC"),
    FAILED( "FAI"),
    SKIPPED( "SKP");
 
    private final String code;
}