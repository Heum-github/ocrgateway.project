package com.cjh.lib.converter.common.utils;

import java.io.File;
import org.jodconverter.core.DocumentConverter;
import org.jodconverter.core.office.OfficeException;
import org.jodconverter.local.LocalConverter;
import org.jodconverter.local.office.LocalOfficeManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class OfficeToPdfConverter implements FileFormatConverter {
    
    private static final Logger logger = LoggerFactory.getLogger(OfficeToPdfConverter.class);

    @Value("${office.home}")
    private String officeHome;    
    
    @Override
    public Boolean convert(String filePath, String outputPath) {        
        LocalOfficeManager officeManager = LocalOfficeManager.builder()
                .officeHome(officeHome)
                .build();
        
        try {
            officeManager.start();
            File inputFile = new File(filePath);
            File outputFile = new File(outputPath);
            
            // 파일 변환기를 생성, 파일 변환을 수행
            DocumentConverter converter = LocalConverter.builder()
                    .officeManager(officeManager)
                    .build();
            
            converter.convert(inputFile).to(outputFile).execute();
            return true;

        } catch (OfficeException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (officeManager != null) {
                try {
                    officeManager.stop();
                } catch (OfficeException e) {
                    logger.error("error :", e);
                    e.printStackTrace();
                }
            }
        }
    }
}
