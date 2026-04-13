package com.cjh.domain.ocr.shared.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OcrTaskTargetFile {

    // 이미지파일ID
    private String imageFileId;
    // 서식코드
    private String formCode;
    // 이미지내 페이지수
    private int imageFileQuantity;

    // 이미지경로명
    private String filePath;
    // 파일확장자명
    private String fileExtension;

    public OcrTaskTargetFile() {
    }   
}
