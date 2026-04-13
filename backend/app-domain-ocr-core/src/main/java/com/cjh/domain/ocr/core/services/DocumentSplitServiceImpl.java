package com.cjh.domain.ocr.core.services;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cjh.common.shared.utils.FileUtils;
import com.cjh.common.shared.utils.PathUtils;
import com.cjh.domain.ocr.core.interfaces.DocumentSplitService;
import com.cjh.lib.converter.common.utils.MultiImageFileSplitter;

@Service
public class DocumentSplitServiceImpl implements DocumentSplitService{
    
    public static final String SPLIT_FILE_EXTENSION     = "png";
    public static final String SPLIT_PDF_FILE_EXTENSION = "pdf";

    private MultiImageFileSplitter _splitter;

    public DocumentSplitServiceImpl(MultiImageFileSplitter splitter) {
        this._splitter = splitter;
    }

    private static final Logger logger = LoggerFactory.getLogger(DocumentSplitServiceImpl.class);
    
    public List<String> split(String filePath, String outputPath) {
        
        boolean result = _splitter.split(filePath, outputPath);
    
        if (!result) {
            logger.error("멀티파일 분할실패");
            return null;
        }
        
        List<String> files = FileUtils.listFiles(outputPath);
    
        if (files == null) {
            logger.error("멀티파일 분할종료 - 저장경로 => " + outputPath + " 파일이 조회되지 않음");
            return null;
        }
    
        //해당 경로에 확장자 PNG인 목록반환
        return files.stream().filter(file -> file.toLowerCase().endsWith(SPLIT_FILE_EXTENSION)).collect(Collectors.toList());
    }

    // DPI 설정
    public List<String> splitV2(String filePath, String outputPath, int density) {
        
        boolean result = _splitter.splitV2(filePath, outputPath, density);
    
        if (!result) {
            logger.error("멀티파일 분할실패");
            return null;
        }
        
        List<String> files = FileUtils.listFiles(outputPath);
    
        if (files == null) {
            logger.error("멀티파일 분할종료 - 저장경로 => " + outputPath + " 파일이 조회되지 않음");
            return null;
        }
    
        //해당 경로에 확장자 PNG인 목록반환
        return files.stream().filter(file -> file.toLowerCase().endsWith(SPLIT_FILE_EXTENSION)).collect(Collectors.toList());
    }

    public List<String> splitPdfToSinglePdfPage(String filePath, String outputPath) {
        
        boolean result = _splitter.splitPdfToSinglePdfPage(filePath, outputPath);
    
        if (!result) {
            logger.error("멀티파일 분할실패");
            return null;
        }
        
        List<String> files = FileUtils.listFiles(outputPath);
    
        if (files == null) {
            logger.error("멀티파일 분할종료 - 저장경로 => " + outputPath + " 파일이 조회되지 않음");
            return null;
        }
    
        //해당 경로에 확장자 PNG인 목록반환
        return files.stream().filter(file -> file.toLowerCase().endsWith(SPLIT_PDF_FILE_EXTENSION)).collect(Collectors.toList());
    }
    
    public int pageCount(String filePath) {
        return _splitter.getPageCount(filePath);
    }
}
