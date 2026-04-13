package com.cjh.lib.converter.common.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jodconverter.core.DocumentConverter;
import org.jodconverter.core.office.OfficeException;
import org.jodconverter.local.LocalConverter;
import org.jodconverter.local.office.LocalOfficeManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RtfToPdfConverter implements FileFormatConverter {
    
    private static final Logger logger = LoggerFactory.getLogger(RtfToPdfConverter.class);

    @Value("${abiword.home}")
    private String officeHome;    
    
    @Override
    public Boolean convert(String filePath, String outputPath) {        
        try {
            List<String> command = new ArrayList<>();
            command.add(officeHome);
            command.add("--to=pdf");
            command.add(filePath);

            // 외부 명령어 실행
            ProcessBuilder processBuilder = new ProcessBuilder(command);
            processBuilder.inheritIO();  // 콘솔에 출력하도록 설정
            Process process = processBuilder.start();

            // 명령어 실행 결과 대기
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                return true;
            } else {
                return false;
            }             
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}