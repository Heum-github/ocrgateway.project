package com.cjh.domain.ocr.core.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cjh.domain.ocr.core.interfaces.ConvertPngService;
import com.cjh.lib.converter.common.utils.FileFormatConverter;
import com.cjh.lib.converter.common.utils.FileFormatConverterContext;
import com.cjh.lib.converter.common.utils.FileFormatPngConverterRegistry;


@Service
public class ConvertPngServiceImpl implements ConvertPngService{
    
    private static final Logger logger = LoggerFactory.getLogger(ConvertPngServiceImpl.class);

    private FileFormatPngConverterRegistry converterRegistry;
    
    public ConvertPngServiceImpl(FileFormatPngConverterRegistry converterRegistry) {
        this.converterRegistry = converterRegistry;
    }

    public boolean convert(String filePath, String outputPath){
        
        FileFormatConverterContext context = new FileFormatConverterContext();
        String fileExtension = FileFormatPngConverterRegistry.getFileExtension(filePath).toLowerCase();
        FileFormatConverter converter = converterRegistry.getConverter(fileExtension);

        if (converter != null) {
            context.setConverter(converter);
            boolean result = context.executeConversion(filePath, outputPath);
            if (result){
                logger.info("png convert success");
                return true;
            } else {
                logger.error("No converter set for the file type");
                return false;
            }
         } else {
            logger.error("No converter available for the file extension");
            return false;
        }
    }
}