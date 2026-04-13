package com.cjh.domain.ocr.core.interfaces;

public interface FileMoveService {
    boolean moveFilesExceptDirectories(String sourceDirPath, String targetDirPath);
}
