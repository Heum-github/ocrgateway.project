package com.cjh.common.shared.vo;

import java.time.LocalDateTime;

public class FileInformation {

    private long fileDetailId;
    private long fileId;
    private String fileType;
    private String fileName;
    private String originalFileName;
    private String filePath;
    private long fileSize;
    private String useYN = "Y";
    private String extension;
    private String fileWebPath;
    private String createUserId;
    private LocalDateTime createDateTime = LocalDateTime.now();

    // Getters and Setters
    public long getFileDetailId() {
        return fileDetailId;
    }

    public void setFileDetailId(long fileDetailId) {
        this.fileDetailId = fileDetailId;
    }

    public long getFileId() {
        return fileId;
    }

    public void setFileId(long fileId) {
        this.fileId = fileId;
    }
    
    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String name) {
        this.fileName = name;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long size) {
        this.fileSize = size;
    }

    public String getUseYN() {
        return useYN;
    }

    public void setUseYN(String useYn) {
        this.useYN = useYn;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public LocalDateTime getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(LocalDateTime createDateTime) {
        this.createDateTime = createDateTime;
    }

    public String getFileWebPath() {
        return fileWebPath;
    }

    public void setFileWebPath(String fileWebPath) {
        this.fileWebPath = fileWebPath;
    }

}
