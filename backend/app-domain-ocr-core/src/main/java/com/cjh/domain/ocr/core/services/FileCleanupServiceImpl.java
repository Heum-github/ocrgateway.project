package com.cjh.domain.ocr.core.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cjh.domain.ocr.core.interfaces.FileCleanupService;
import com.cjh.domain.ocr.shared.vo.OcrTask;
import com.cjh.domain.ocr.shared.vo.OcrTaskTargetFile;

@Service
public class FileCleanupServiceImpl implements FileCleanupService {
    
    private static final Logger logger = LoggerFactory.getLogger(FileCleanupServiceImpl.class);

    public boolean cleanUp(OcrTask ocrTask) {
        boolean result = true;

        if(ocrTask == null)
            return false;

        // cleanUpFile(ocrTask.getDownloadFiles());
        cleanUpFile(ocrTask.getTargetFiles());

        return result;
    }

    private boolean cleanUpFile(List<OcrTaskTargetFile> files) {
        boolean result = true;
            
        if(files == null || files.size() <= 0)
            return true;

        for (OcrTaskTargetFile file : files) {
            // cleanUp(file.getFullFilePath());
            // cleanUp(file.getDecryptedFullFilePath());
        }

        return result;
    }

    // 파일 경로에 위치한 파일 삭제
    public boolean cleanUp(String filePath) {
        boolean rtn_boolean = false;

        if(StringUtils.isBlank(filePath)){
            return false;
        }
        try {
            Path path = Paths.get(filePath);
            if (Files.exists(path)) {
                Files.deleteIfExists(path);
                logger.info("파일 삭제 성공: {}", filePath);
                rtn_boolean = true;
            } else {
                logger.warn("경로에서 파일을 찾을 수 없습니다 : {}", filePath);
                rtn_boolean = false;
            }
        } catch (IOException e) {
            logger.error("파일삭제오류발생: " + filePath, e);
            rtn_boolean = false;
        }

        return rtn_boolean;
    }
    
    // 파일 경로 배열에 위치한 파일 삭제
    public boolean cleanUp(List<String> filePaths) {
        if (filePaths == null || filePaths.size() <= 0) {
            logger.warn("파일 경로가 비어있습니다");
            return false;
        }
        for (String filePath : filePaths) {
            boolean result = cleanUp(filePath);
            if (!result) {
                logger.error("파일 삭제 실패: {}", filePath);
                return false;
            }
        }
        return true;
    }

    // 디렉토리 삭제 (해당 디렉토리 + 하위 파일, 폴더)
    public boolean cleanUpDirectory(String dirPath) {
        boolean rtn_boolean = false;

        try {
            
            File fileOrDirectory = new File(dirPath);
            File directory;
    
            if (fileOrDirectory.isDirectory()) {
                directory = fileOrDirectory;
            } else {
                directory = new File(fileOrDirectory.getParent());
            }

            if (directory.exists()) {
                FileUtils.cleanDirectory(directory); // 디렉토리 내부 파일 및 하위 디렉토리 삭제
                FileUtils.deleteDirectory(directory); // 디렉토리 삭제
    
                if (!directory.exists()) {
                    logger.info("디렉토리 삭제 성공: {}", dirPath);
                    rtn_boolean = true;
                } else {
                    logger.info("디렉토리 삭제가 올바르게 처리되지 않았습니다: {}", dirPath);
                    rtn_boolean = false;
                }
            } else {
                logger.warn("경로에서 디렉토리를 찾을 수 없습니다: {}", dirPath);
                rtn_boolean = false;
            }
        } catch (IOException e) {
            logger.error("파일삭제오류발생", e);
            rtn_boolean = false;
        }
        return rtn_boolean;
    }

    // 디렉토리 삭제 (해당 디렉토리 + 하위 파일, 폴더)
    public boolean cleanUpDirectoryV2(String dirPath, boolean removeRootDir) {
        boolean rtn_boolean = false;

        try {
            
            File fileOrDirectory = new File(dirPath);
            File directory;
    
            if (!fileOrDirectory.exists()) {
                logger.warn("경로에서 디렉토리를 찾을 수 없습니다: {}", dirPath);
                rtn_boolean = false;
            } else if (!fileOrDirectory.isDirectory()) {
                logger.warn("대상 경로가 디렉토리가 아닙니다: {}", dirPath);
                rtn_boolean = false;
            } else if (fileOrDirectory.listFiles() == null) {
                logger.warn("대상 경로가 비어있습니다: {}", dirPath);
                rtn_boolean = false;
            } else {
                directory = fileOrDirectory;

                FileUtils.cleanDirectory(directory); // 디렉토리 내부 파일 및 하위 디렉토리 삭제

                if (removeRootDir) {

                    FileUtils.deleteDirectory(directory); // 디렉토리 삭제

                    if (!directory.exists()) {
                        logger.info("디렉토리 삭제 성공: {}", dirPath);
                        rtn_boolean = true;
                    } else {
                        logger.warn("디렉토리 삭제가 올바르게 처리되지 않았습니다: {}", dirPath);
                        rtn_boolean = false;
                    }
                } else {

                    File[] childern = directory.listFiles();

                    if (childern == null || childern.length == 0) {
                        logger.info("디렉토리 삭제 성공: {}", dirPath);
                        rtn_boolean = true;

                    } else {

                        for(var dir : directory.listFiles()) {
                            logger.warn("파일 잔존: {}", dir.getName());
                        }
                        
                        logger.warn("디렉토리 삭제가 올바르게 처리되지 않았습니다: {}", dirPath);
                        rtn_boolean = false;
                    }
                }
            }
        } catch (IOException e) {
            logger.error("파일삭제오류발생", e);
            rtn_boolean = false;
        }
        return rtn_boolean;
    }

    // 임시 파일 삭제 (경로 배열 기반)
    public boolean cleanUpBackup(String[] dirPaths) {
        if (dirPaths == null || dirPaths.length == 0) {
            logger.warn("임시파일 삭제 대상 경로가 비어있습니다.");
            return false;
        }

        boolean allSuccess = true;

        for (String dirPath : dirPaths) {
            if (StringUtils.isBlank(dirPath)) {
                logger.warn("경로에 파일이 존재하지 않습니다 - skip");
                continue;
            }

            File dir = new File(dirPath);
            if (!dir.exists() || !dir.isDirectory()) {
                logger.warn("디렉토리가 존재하지 않거나 디렉토리가 아님 - skip: {}", dirPath);
                allSuccess = false;
                continue;
            }

            try {
                File[] files = dir.listFiles(File::isFile);
                if (files == null || files.length == 0) {
                    logger.info("삭제할 파일이 없습니다 - {}", dirPath);
                    continue;
                }

                for (File file : files) {
                    if (!file.delete()) {
                        logger.error("파일 삭제 실패: {}", file.getAbsolutePath());
                        allSuccess = false;
                    }
                }
                logger.info("파일 삭제 완료 - {}", dirPath);

            } catch (Exception e) {
                logger.error("예외 발생 - {}: {}", dirPath, e.getMessage());
                allSuccess = false;
            }
        }

        return allSuccess;
    }

    public boolean cleanUpDirectorySafe(String dirPath, boolean removeRootDir) {

        if (StringUtils.isBlank(dirPath)) {
            logger.warn("디렉토리 경로가 비어있습니다");
            return false;
        }

        File directory = new File(dirPath);

        // 1. 존재 + 디렉토리 체크
        if (!directory.exists()) {
            logger.info("삭제 대상 디렉토리가 존재하지 않습니다: {}", dirPath);
            return true; // 👉 “그냥 지나가기” 요구사항 반영
        }

        if (!directory.isDirectory()) {
            logger.warn("대상 경로가 디렉토리가 아닙니다: {}", dirPath);
            return false;
        }

        try {
            // 2. 내부 파일 삭제
            FileUtils.cleanDirectory(directory);

            // 3. 루트 삭제 여부
            if (removeRootDir) {
                FileUtils.deleteDirectory(directory);
                logger.info("디렉토리 삭제 성공: {}", dirPath);
            } else {
                logger.info("디렉토리 내부 파일 삭제 완료: {}", dirPath);
            }

            return true;

        } catch (IOException e) {
            logger.error("디렉토리 삭제 중 오류 발생: {}", dirPath, e);
            return false;
        }
    }
}
