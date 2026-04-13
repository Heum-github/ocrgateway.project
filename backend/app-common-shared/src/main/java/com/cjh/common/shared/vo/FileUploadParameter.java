package com.cjh.common.shared.vo;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class FileUploadParameter {
    
    private String fileName;
    private MultipartFile file;
    private String saveHistory;
    private long fileDetailId;
    private long fileId;
    private List<MultipartFile> files;

    public List<MultipartFile> getFiles() {
        return files;
    }
    public void setFiles(List<MultipartFile> files) {
        this.files = files;
    }
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
    public String getSaveHistory() {
        return saveHistory;
    }
    public void setSaveHistory(String historyYN) {
        this.saveHistory = historyYN;
    }
    public String getFileName() {
        return fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    public MultipartFile getFile() {
        return file;
    }
    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
