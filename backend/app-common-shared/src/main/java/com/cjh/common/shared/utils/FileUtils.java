package com.cjh.common.shared.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUtils {

    private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);

    public static boolean isFileExists(String filePath) {

        if(StringUtils.isBlank(filePath)){
            return false;
        }

        File file = new File(filePath);
        return file.exists() && file.isFile();
    }

    public static boolean createDirectories(Path path) {

        if (Files.exists(path)) {
            return true;
        }    

        try {
            Files.createDirectories(path);
            logger.info("디렉토리가 생성되었습니다 => " + path);
            return true;
        } catch (IOException e) {
            logger.error("디렉토리가 생성중 오류가 발생하였습니다 => " + e.getMessage());
            return false;
        } catch (Exception e) {
            logger.error("디렉토리가 생성중 오류가 발생하였습니다 => " + e.getMessage());
            return false;
        }
    }

    public static List<String> listFiles(String directoryPath) {

        if (StringUtils.isBlank(directoryPath)) {
            return null;
        }

        File directory = new File(directoryPath);
        if (!directory.exists() || !directory.isDirectory()) {
            logger.error("유효하지 않은 디렉토리 경로입니다: " + directoryPath);
            return null;
        }

        File[] files = directory.listFiles();
        if (files == null || files.length == 0) {
            return null;
        }

        List<String> filePaths = new ArrayList<>();

        for (File file : files) {
            if (file.isFile()) {
                filePaths.add(file.getAbsolutePath());
            }
        }

        return filePaths;
    }

    public static List<String> listFiles(String directoryPath, String fileName) {
        List<String> filePaths = new ArrayList<>();
        
        if (StringUtils.isBlank(directoryPath)) {
            return filePaths;
        }

        File directory = new File(directoryPath);
        if (!directory.exists() || !directory.isDirectory()) {
            logger.error("유효하지 않은 디렉토리 경로입니다: " + directoryPath);
            return filePaths;
        }

        File[] files = directory.listFiles();
        if (files == null || files.length == 0) {
            return filePaths;
        }

        for (File file : files) {
            if (file.isFile() && (StringUtils.isBlank(fileName) || file.getName().startsWith(fileName))) {
                filePaths.add(file.getAbsolutePath());
            }
        }

        return filePaths;
    }

    public static boolean deleteFile(String filePath) {
        if (StringUtils.isBlank(filePath)) {
            logger.error("파일 경로가 비어있습니다: " + filePath);
            return false;
        }

        File file = new File(filePath);
        if (!file.exists()) {
            logger.warn("삭제하려는 파일이 존재하지 않습니다: " + filePath);
            return false;
        }

        try {
            boolean result = Files.deleteIfExists(file.toPath());
            if (result) {
                logger.info("파일이 삭제되었습니다: " + filePath);
            } else {
                logger.warn("파일 삭제 실패: " + filePath);
            }
            return result;
        } catch (IOException e) {
            logger.error("파일 삭제 중 오류 발생: " + e.getMessage());
            return false;
        }
    }

}
