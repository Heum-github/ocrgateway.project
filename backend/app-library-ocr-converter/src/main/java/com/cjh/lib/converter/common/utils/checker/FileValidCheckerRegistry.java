package com.cjh.lib.converter.common.utils.checker;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class FileValidCheckerRegistry {
    private final Map<String, FileValidChecker> cherckerMap;

    public FileValidCheckerRegistry(PdfValidChecker pdfChecker) {
        this.cherckerMap = new HashMap<>();
        initialize(pdfChecker);
    }

    private void initialize(PdfValidChecker pdfChecker) 
    {
        cherckerMap.put("pdf", pdfChecker);
    }
    
    // 확장자에 따른 컨버터 가져오는 메소드
    public FileValidChecker getChecker(String fileExtension) {
        return cherckerMap.get(fileExtension);
    }

    // 확장자를 가져오는 메소드
    public static String getFileExtension(String filePath) {
        int dotIndex = filePath.lastIndexOf('.');
        return dotIndex == -1 ? "" : filePath.substring(dotIndex + 1);
    }
}
