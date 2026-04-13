package com.cjh.common.shared.vo;

public class FileStorageResult {
    
    private String fileName;
    private String fileWebPath;
    private String filePath;
    private String originalFileName;
    private long fileSize;
    private String fileExtension;

    public FileStorageResult(String filePath, String originalFileName) {
        this.filePath = filePath;
        this.originalFileName = originalFileName;
    }

    public FileStorageResult(String filePath, String fileWebPath, String fileName, String originalFileName, long fileSize, String fileExtension) {
        this.filePath = filePath;
        this.fileWebPath = fileWebPath;
        this.originalFileName = originalFileName;
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.fileExtension = fileExtension;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    public String getFileWebPath() {
        return fileWebPath;
    }

    public void setFileWebPath(String fileWebPath) {
        this.fileWebPath = fileWebPath;
    }
}
