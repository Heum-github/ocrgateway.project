package com.cjh.domain.ocr.core.services;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cjh.domain.ocr.core.interfaces.FileMoveService;

@Service
public class FileMoveServiceImpl implements FileMoveService {
    
    private static final Logger logger = LoggerFactory.getLogger(FileMoveServiceImpl.class);

    public boolean moveFilesExceptDirectories(String sourceDirPath, String targetDirPath) {
        
        boolean result = true;

        Path sourceDir = Paths.get(sourceDirPath);
        Path targetDir = Paths.get(targetDirPath);

        if(StringUtils.isBlank(sourceDirPath)){
            logger.warn("원본 파일 경로가 없습니다.");
            return false;
        }

        if (!Files.exists(sourceDir) || !Files.isDirectory(sourceDir)) {
            logger.warn("원본 파일 경로가 없습니다.");
            return false;
        }

        if (!Files.exists(targetDir)) {
            try {
                Files.createDirectories(targetDir);
            } catch (Exception e) {
                logger.error("대상 디렉토리 생성 실패 -> " + targetDirPath);
            }
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");

        try (Stream<Path> paths = Files.list(sourceDir)) {
            paths.filter(Files::isRegularFile)
                 .forEach(path -> {
                    try {
                        String originalName = path.getFileName().toString();

                        // 확장자 분리
                        String ext = "";
                        int dotIdx = originalName.lastIndexOf('.');
                        if (dotIdx != -1) {
                            ext = originalName.substring(dotIdx);
                        }

                        // 실행 시각 기반 파일명 생성
                        String timestampName = LocalDateTime.now().format(formatter) + ext;

                        // 타겟 경로
                        Path targetPath = targetDir.resolve(timestampName);

                        // 파일 이동 + 이름 변경
                        Files.move(path, targetPath, StandardCopyOption.REPLACE_EXISTING);

                        logger.debug("파일 이동 및 이름 변경 : " + path + " -> " + targetPath);

                    } catch (Exception e) {
                        logger.error("파일 이동 실패 : " + path);
                    }
                 });
        } catch(Exception e) {
            logger.error("파일 이동 중 에러 발생");
            e.printStackTrace();
            result = false;
        }

        return result;
    }
}
