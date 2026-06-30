package com.cjh.domain.ocr.core.services;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.cjh.common.shared.utils.PathUtils;
import com.cjh.domain.ocr.core.interfaces.FileParsingService;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class FileParsingServiceImpl implements FileParsingService{

    // 허용할 확장자 목록
    private static final Set<String> ALLOWED_EXTENSIONS = Set.of("csv", "txt", "out");

    public List<String> parseImageKeyFile(MultipartFile file) throws Exception {
        validateFileExtension(file.getOriginalFilename());

        List<String> imageKeys = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
            
            String line;
            while ((line = reader.readLine()) != null) {
                // 한 줄을 읽어와서 쉼표(,)로 분리
                String[] keys = line.split(",");
                
                for (String key : keys) {
                    String trimmedKey = key.trim();
                    // 빈 값이 아닌 정상적인 키만 리스트에 추가
                    if (StringUtils.hasText(trimmedKey)) {
                        imageKeys.add(trimmedKey);
                    }
                }
            }
        }
        
        return imageKeys;
    }

    private void validateFileExtension(String filename) {
        if (!StringUtils.hasText(filename)) {
            throw new IllegalArgumentException("파일명이 존재하지 않습니다.");
        }

        // PathUtils(Apache Commons IO)를 사용하여 확장자 추출 (코드가 한 줄로 획기적으로 줄어듦)
        String extension = PathUtils.extractExtension(filename).toLowerCase();

        if (!ALLOWED_EXTENSIONS.contains(extension)) {
            throw new IllegalArgumentException("지원하지 않는 파일 형식입니다. 허용되는 확장자: " + ALLOWED_EXTENSIONS);
        }
    }
}