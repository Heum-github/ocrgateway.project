package com.cjh.lib.converter.common.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFPrintSetup;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jodconverter.core.DocumentConverter;
import org.jodconverter.core.document.DefaultDocumentFormatRegistry;
import org.jodconverter.core.document.DocumentFormat;
import org.jodconverter.core.office.OfficeException;
import org.jodconverter.core.util.FileUtils;
import org.jodconverter.local.LocalConverter;
import org.jodconverter.local.office.LocalOfficeManager;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFPrintSetup;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ExcelToPdfConverter implements FileFormatConverter {
    
    private static final Logger logger = LoggerFactory.getLogger(HtmlToPdfConverter.class);
    @Value("${office.home}")
    private String officeHome;
    
    private final String HSSF_EXTENSION = "xls";
    private final String XSSF_EXTENSION = "xlsx";

    @Override
    public Boolean convert(String filePath, String outputPath) {

        LocalOfficeManager officeManager = LocalOfficeManager.builder().officeHome(officeHome).build();
        InputStream nis = null;

        try (InputStream fis = new FileInputStream(filePath);
             FileOutputStream fos = new FileOutputStream(outputPath);
             ByteArrayOutputStream bos = new ByteArrayOutputStream()) {

            officeManager.start();
            final DocumentConverter converter = LocalConverter.builder().officeManager(officeManager).build();
            final DocumentFormat sourceFormat = DefaultDocumentFormatRegistry.getFormatByExtension(FilenameUtils.getExtension(filePath));
            final DocumentFormat targetFormat = DefaultDocumentFormatRegistry.getFormatByExtension(FilenameUtils.getExtension(outputPath));
            nis = getExcelInputStream(filePath, fis, bos);
            converter.convert(nis).as(sourceFormat).to(fos).as(targetFormat).execute();
            return true;
        } catch (OfficeException e1) {
            logger.error("오피스 에러 발생 => :", e1);
            e1.printStackTrace();
            return false;
        } catch (IOException e2) {
            logger.error("IO 에러 발생 => :", e2);
            e2.printStackTrace();
            return false;
        } catch (Exception e3) {
            logger.error("미정의 에러 발생 => :", e3);
            e3.printStackTrace();
            return false;
        } finally {
            if (officeManager != null) {
                try {
                    officeManager.stop();
                } catch (OfficeException e4) {
                    logger.error("오피스 에러 발생 => :", e4);
                    e4.printStackTrace();
                }
            }

            if(nis != null) {
                try {
                    nis.close();
                } catch (Exception e5) {
                    logger.error("인풋스트림 에러 발생 => :", e5);
                    e5.printStackTrace();
                }
            }
        }
    }   

    private InputStream getExcelInputStream(String inputPath, InputStream fis, ByteArrayOutputStream bos) throws Exception {

        if(FileUtils.getExtension(inputPath).equals(HSSF_EXTENSION)) {
            HSSFWorkbook hssfWorkbook = (HSSFWorkbook) WorkbookFactory.create(fis);
            Iterator<Sheet> iterator = hssfWorkbook.sheetIterator();
            while (iterator.hasNext()) {
                HSSFSheet hssfSheet =  (HSSFSheet) iterator.next();
                //disableComments(hssfSheet);
                hssfSheet.setAutobreaks(true);
                hssfSheet.setFitToPage(true);
                HSSFPrintSetup hssfPrintSetup = hssfSheet.getPrintSetup();
                hssfPrintSetup.setFitWidth((short) 1);
                hssfPrintSetup.setFitHeight((short) 1);
            }
            hssfWorkbook.write(bos);
            fis = new ByteArrayInputStream(bos.toByteArray());
            hssfWorkbook.close();
        } else if (FileUtils.getExtension(inputPath).equals(XSSF_EXTENSION)) {
            XSSFWorkbook xssfWorkbook = (XSSFWorkbook) WorkbookFactory.create(fis);
            Iterator<Sheet> iterator = xssfWorkbook.sheetIterator();
            while (iterator.hasNext()) {
                XSSFSheet xssfSheet = (XSSFSheet) iterator.next();
                //disableComments(xssfSheet);
                xssfSheet.setAutobreaks(true);
                xssfSheet.setFitToPage(true);
                XSSFPrintSetup printSetup = xssfSheet.getPrintSetup();
                printSetup.setFitWidth((short) 1);
                printSetup.setFitHeight((short) 1);
            }
            xssfWorkbook.write(bos);
            fis = new ByteArrayInputStream(bos.toByteArray());
            xssfWorkbook.close();
        } 

        return fis;
    }
}