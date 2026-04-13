package com.cjh.lib.converter.common.utils;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class FileFormatPngConverterRegistry {
    private final Map<String, FileFormatConverter> converterMap;

    public FileFormatPngConverterRegistry(ImageToPdfConverter imageToPdfConverter) {

        this.converterMap = new HashMap<>();
        initialize( imageToPdfConverter);
    }

    private void initialize( ImageToPdfConverter imageToPdfConverter) 
    {
        converterMap.put("tif",  imageToPdfConverter);
        converterMap.put("tiff", imageToPdfConverter);
        converterMap.put("png",  imageToPdfConverter);
        converterMap.put("jpg",  imageToPdfConverter);
        converterMap.put("jpeg", imageToPdfConverter);
        converterMap.put("bmp",  imageToPdfConverter);
        converterMap.put("ico",  imageToPdfConverter);
        converterMap.put("gif",  imageToPdfConverter);
        converterMap.put("svg",  imageToPdfConverter);
        converterMap.put("heic", imageToPdfConverter);
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
