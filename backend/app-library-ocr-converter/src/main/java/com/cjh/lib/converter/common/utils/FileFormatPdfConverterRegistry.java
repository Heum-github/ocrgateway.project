package com.cjh.lib.converter.common.utils;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class FileFormatPdfConverterRegistry {
    private final Map<String, FileFormatConverter> converterMap;

    public FileFormatPdfConverterRegistry(OfficeToPdfConverter officeToPdfConverter,
                             ExcelToPdfConverter excelToPdfConverter,
                             HtmlToPdfConverter htmlToPdfConverter,
                             ImageToPdfConverter imageToPdfConverter,
                             PdfToPdfConverter pdfToPdfConverter,
                             RtfToPdfConverter rtfToPdfConverter) {
        this.converterMap = new HashMap<>();
        initialize(officeToPdfConverter, excelToPdfConverter, htmlToPdfConverter, imageToPdfConverter, pdfToPdfConverter, rtfToPdfConverter);
    }

    private void initialize( OfficeToPdfConverter officeToPdfConverter,
                             ExcelToPdfConverter excelToPdfConverter,
                             HtmlToPdfConverter htmlToPdfConverter,
                             ImageToPdfConverter imageToPdfConverter,
                             PdfToPdfConverter pdfToPdfConverter,
                             RtfToPdfConverter rtfToPdfConverter) 
                            {
        converterMap.put("xlsx", excelToPdfConverter);
        converterMap.put("xls", excelToPdfConverter);
        converterMap.put("doc", officeToPdfConverter);
        converterMap.put("docx", officeToPdfConverter);
        converterMap.put("ppt", officeToPdfConverter);
        converterMap.put("pptx", officeToPdfConverter);
        converterMap.put("html", htmlToPdfConverter);
        converterMap.put("htm", htmlToPdfConverter);
        converterMap.put("tif", imageToPdfConverter);
        converterMap.put("tiff", imageToPdfConverter);
        converterMap.put("png", imageToPdfConverter);
        converterMap.put("jpg", imageToPdfConverter);
        converterMap.put("jpeg", imageToPdfConverter);
        converterMap.put("bmp", imageToPdfConverter);
        converterMap.put("ico", imageToPdfConverter);
        converterMap.put("gif", imageToPdfConverter);
        converterMap.put("svg", imageToPdfConverter);
        converterMap.put("pdf", pdfToPdfConverter);
        converterMap.put("PDF", pdfToPdfConverter);
        converterMap.put("rtf", rtfToPdfConverter);
        converterMap.put("txt", officeToPdfConverter);
        //converterMap.put("pdf", pdfToPngConverter);
    }
    
    // 확장자에 따른 컨버터 가져오는 메소드
    public FileFormatConverter getConverter(String fileExtension) {
        return converterMap.get(fileExtension);
    }

    // 확장자를 가져오는 메소드
    public static String getFileExtension(String filePath) {
        int dotIndex = filePath !=null ? filePath.lastIndexOf('.') : -1;
        return dotIndex == -1 ? "" : filePath.substring(dotIndex + 1);
    }
}
