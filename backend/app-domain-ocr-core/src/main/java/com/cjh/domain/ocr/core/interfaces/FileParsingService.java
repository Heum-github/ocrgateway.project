package com.cjh.domain.ocr.core.interfaces;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface FileParsingService {
    public List<String> parseImageKeyFile(MultipartFile file) throws Exception;
}
