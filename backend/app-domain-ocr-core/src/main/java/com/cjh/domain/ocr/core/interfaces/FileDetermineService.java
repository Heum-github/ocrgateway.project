package com.cjh.domain.ocr.core.interfaces;

import java.io.IOException;

public interface FileDetermineService {
    public String replaceExtension(String fullPath, String newExt);
    public String detectExtension(String filePath);
    public void renameFile(String from, String to) throws IOException;
}
