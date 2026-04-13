package com.cjh.domain.ocr.core.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cjh.common.shared.utils.PathUtils;
import com.cjh.domain.ocr.core.interfaces.ConvertPdfService;
import com.cjh.lib.converter.common.utils.FileFormatConverter;
import com.cjh.lib.converter.common.utils.FileFormatConverterContext;
import com.cjh.lib.converter.common.utils.FileFormatPdfConverterRegistry;

@Service
public class ConvertPdfServiceImpl implements ConvertPdfService{
    
    private static final Logger logger = LoggerFactory.getLogger(ConvertPdfServiceImpl.class);

    private FileFormatPdfConverterRegistry converterRegistry;
    
    public ConvertPdfServiceImpl(FileFormatPdfConverterRegistry converterRegistry) {
        this.converterRegistry = converterRegistry;
    }

    @Override
    public boolean convert(String filePath, String outputPath){
        
        FileFormatConverterContext context = new FileFormatConverterContext();
        String fileExtension = FileFormatPdfConverterRegistry.getFileExtension(filePath).toLowerCase();
        FileFormatConverter converter = converterRegistry.getConverter(fileExtension);

        if (converter != null) {
            
            PathUtils.createDirectoryIfNotExists(filePath);

            context.setConverter(converter);
            boolean result = context.executeConversion(filePath, outputPath);

            if (result){
                logger.info("PDF convert success");
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
