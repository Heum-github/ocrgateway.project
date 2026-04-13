
package com.cjh.common.shared.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class ImageToBase64Utils {

    private static final Logger logger = LoggerFactory.getLogger(ImageToBase64Utils.class);
    //private static final String DEFAULT_EXTENSION = ".png";

    public static Map<String, String> encodeImageToBase64(String filePath) {
        try {
            logger.info("base64 변환 시작");

            File file = new File(filePath);

            if (!file.exists() || !file.canRead()) {
                logger.error("파일이 존재하지 않거나 읽을 수 없습니다: " + filePath);
                return null;
            }

            String fileName = file.getName();
            logger.info("base64 변환 - 파일명 => " + fileName);
            
            byte[] fileContent = Files.readAllBytes(file.toPath());
            String base64String = Base64.getEncoder().encodeToString(fileContent);
            Map<String, String> result = new HashMap<>();
            result.put("base64String", base64String);
            result.put("fileName", fileName);
            logger.info("base64 변환 종료");

            return result;

        }catch(Exception e){
            logger.error("base64 변환오류",e);
            return null;
        }
    }

    //2024-08-07 모든 확장자 풀에 대해 Base64 변환하는 V2로 변경
    public static List<String> encodeImageFilesToBase64(String directoryPath, List<String> extensions) throws IOException {

        try{
            
            logger.info("base64 변환 시작");

            List<File> resultFiles = new ArrayList<File>();
            List<String> resultList = new ArrayList<String>();
            
            File directory = new File(directoryPath);

            if (!directory.exists()) {
                throw new IllegalArgumentException("디렉토리가 존재하지 않습니다");
            }
            for(String ext : extensions) {
                File[] targetFiles = directory.listFiles((dir, name) -> name.toLowerCase().endsWith(ext));

                if(targetFiles != null && targetFiles.length > 0){
                    resultFiles.addAll(Arrays.asList(targetFiles));
                }
            }
            
            if (resultFiles.size() == 0) {
                logger.info("base64 변환 오류 - 대상 파일이 없습니다");
                return null;
            }

            // 파일을 숫자 순서로 정렬
            FilePathSorter.sortFilesByNumberOrderV2(resultFiles);

            logger.info("base64 변환 진행 - 대상 파일갯수 => " + resultFiles.size());

            for (File file : resultFiles) {
                logger.info("base64 변환 - 파일명 => " +file.getName());
                byte[] fileContent = Files.readAllBytes(file.toPath());
                String base64String = Base64.getEncoder().encodeToString(fileContent);
                resultList.add(base64String);
            }

            logger.info("base64 변환 종료");
            return resultList;
        }catch(IllegalArgumentException e){
            logger.error("base64 변환오류",e);
            return null;
        }catch(Exception e){
            logger.error("base64 변환오류",e);
            return null;
        }
    }

}
