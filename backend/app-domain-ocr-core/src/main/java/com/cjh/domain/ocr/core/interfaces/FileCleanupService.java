package com.cjh.domain.ocr.core.interfaces;

import java.util.List;

import com.cjh.domain.ocr.shared.vo.OcrTask;

public interface FileCleanupService {
    public boolean cleanUp(OcrTask ocrTask);
    
    public boolean cleanUp(String filePath);
    
    public boolean cleanUp(List<String> filePaths);
    
    public boolean cleanUpDirectory(String dirPath);

    public boolean cleanUpDirectoryV2(String dirPath, boolean removeRootDir); 

    public boolean cleanUpDirectorySafe(String dirPath, boolean removeRootDir);

    public boolean cleanUpBackup(String[] dirPaths);
}
