package com.cjh.lib.converter.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ImageToPdfConverter implements FileFormatConverter {

    private static final Logger logger = LoggerFactory.getLogger(ImageToPdfConverter.class);

    @Value("${magick.path}")
    private String magickPath;

    public Boolean convert(String filePath, String outputPath) {

        List<String> command = new ArrayList<>();
        command.add(magickPath);
        command.add(filePath);
//        command.add("-resize");
//        command.add("200x200");
        command.add(outputPath);

        try {
            ProcessBuilder processBuilder = new ProcessBuilder(command);
            Process process = processBuilder.start();

			// 출력 및 오류 처리
            BufferedReader outputReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder outputBuilder = new StringBuilder();
            String line;
            while ((line = outputReader.readLine()) != null) {
                outputBuilder.append(line).append("\n");
            }

            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            StringBuilder errorBuilder = new StringBuilder();
            while ((line = errorReader.readLine()) != null) {
                errorBuilder.append(line).append("\n");
            }

            int exitCode = process.waitFor();
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