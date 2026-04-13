package com.cjh.lib.converter.common.utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jodconverter.core.DocumentConverter;
import org.jodconverter.core.office.OfficeException;
import org.jodconverter.local.LocalConverter;
import org.jodconverter.local.office.LocalOfficeManager;

public class WordToPdfOpenOfficeConverter {

    // TODO ConverterOption 파라메터 추가 - 오피스경로, 소스파일경로, 변환파일경로 처리
    public static void convert() {
        
        String officeHome = "C:\\Program Files (x86)\\OpenOffice 4";
        //String officeHome = "/opt/openoffice4/program";
        //String officeHome = "/usr/lib/libreoffice/program";
        //String officeHome = "libreoffice";
        //String officeHome = "/usr/lib/libreoffice";
        
        LocalOfficeManager officeManager = LocalOfficeManager.builder()
                .officeHome(officeHome)
                .build();
        
        try {
            officeManager.start();
            
            // 워드 파일(.docx)과 출력될 PDF 파일의 경로를 지정합니다.
            File inputFile = new File("D:\\test\\test.doc");
            //File inputFile = new File("/mnt/d/test/test.doc");
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            File outputFile = new File("D:\\test\\test_"+sdf.format(new Date())+".pdf");
            //File outputFile = new File("/mnt/d/test/test_"+sdf.format(new Date())+".pdf");
            
            // 문서 변환기를 생성하고 파일 변환을 수행합니다.
            DocumentConverter converter = LocalConverter.builder()
                    .officeManager(officeManager)
                    .build();

            converter.convert(inputFile).to(outputFile).execute();
            
            //System.out.println("success " + outputFile);

        } catch (OfficeException e) {
            e.printStackTrace();
        } finally {
            if (officeManager != null) {
                try {
                    officeManager.stop();
                } catch (OfficeException e) {
                    e.printStackTrace(); // 실패한 경우 로그 출력
                }
            }
        }
    }
}
