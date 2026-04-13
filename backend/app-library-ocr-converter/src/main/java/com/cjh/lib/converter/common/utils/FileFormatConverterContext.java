package com.cjh.lib.converter.common.utils;

public class FileFormatConverterContext {
    private FileFormatConverter converter;

    public void setConverter(FileFormatConverter converter) {
        this.converter = converter;
    }

    public Boolean executeConversion(String filePath, String outputPath) {
        if (converter == null) {
            return false;
        }
        return converter.convert(filePath, outputPath);
    }
}
