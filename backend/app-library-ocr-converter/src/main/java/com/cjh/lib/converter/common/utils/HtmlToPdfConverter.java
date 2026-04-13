package com.cjh.lib.converter.common.utils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class HtmlToPdfConverter implements FileFormatConverter {

    private static final Logger logger = LoggerFactory.getLogger(HtmlToPdfConverter.class);

    @Override
    public Boolean convert(String filePath, String outputPath) {
        try {           
            // wkhtmltopdf 명령어 구성
            String command = "wkhtmltopdf " + filePath + " " + outputPath;
            
            // 프로세스 실행
            Process process = Runtime.getRuntime().exec(command);

            // 프로세스의 완료를 기다림
            int exitCode = process.waitFor();

            // 프로세스 실행 결과 확인
            if (exitCode == 0) {
                return true;
            } else {
                return false;
            }
        } catch (IOException | InterruptedException e) {
            logger.error("error :", e);
            e.printStackTrace();
            return false;
        }
    }
}
