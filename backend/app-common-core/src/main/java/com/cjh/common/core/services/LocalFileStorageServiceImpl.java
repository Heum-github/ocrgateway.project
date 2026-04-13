package com.cjh.common.core.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cjh.common.core.interfaces.FileStorageService;
import com.cjh.common.shared.exception.AppBaseException;
import com.cjh.common.shared.vo.FileStorageResult;

@Service
public class LocalFileStorageServiceImpl implements FileStorageService{

    private static final Logger logger = LoggerFactory.getLogger(LocalFileStorageServiceImpl.class);

    public FileStorageResult storeFile(MultipartFile file, String path, boolean randomFileName) {
        
        try {
            
            logger.info("파일 저장 시작 - 저장경로 => " + path);
            if (file.isEmpty()) {
                logger.error("파일 저장 시작 - 파일이 비어있음");
                throw new IOException("파일이 없습니다.");
            }

            Path destinationPath = Paths.get(path).toAbsolutePath().normalize();

            logger.info("파일 저장 진행 - 저장절대경로 => " + destinationPath);
            Files.createDirectories(destinationPath);

            String fileName = file.getOriginalFilename();
            if (randomFileName) {
                fileName = generateRandomFileName(fileName);
            }

            Path targetLocation = destinationPath.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            logger.info("파일 저장 완료 - 저장완료경로 => " + targetLocation.toString());

            return new FileStorageResult(targetLocation.toString(), fileName);

        } catch (IOException e) {
            logger.error("파일저장오류", e);
        } catch (AppBaseException e) {
            logger.error("파일저장오류", e);
        }
        return null;
    }

    private String generateRandomFileName(String originalFileName) {
        String fileExtension = "";
        int dotIndex = originalFileName.lastIndexOf(".");
        if (dotIndex > 0) {
            fileExtension = originalFileName.substring(dotIndex);
        }
        return UUID.randomUUID().toString().replace("-", "") + fileExtension;
    }
}
