package com.cjh.lib.converter.common.utils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class PdfToPdfConverter implements FileFormatConverter {

    private static final Logger logger = LoggerFactory.getLogger(PdfToPdfConverter.class);

    @Override
    public Boolean convert(String filePath, String outputPath) {               
        try {
            // Convert filePath to Path object
            Path sourcePath = new File(filePath).toPath();
            Path outputFile = new File(outputPath).toPath();

            Files.copy(sourcePath, outputFile);
            return true;

        } catch (Exception e) {
            logger.error("error :", e);
            e.printStackTrace();
            return false;
        }
    }
}
