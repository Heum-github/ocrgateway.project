package com.cjh.common.shared.vo;

public class FileInformationSearchParameter {
    
    private long fileDetailId;
    private long fileId;
    private long[] fileIds;

    public FileInformationSearchParameter() {
    }

    public FileInformationSearchParameter(long fileId) {
        this.fileId = fileId;
    }

    public FileInformationSearchParameter(long[] fileIds) {
        this.fileIds = fileIds;
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

    public long[] getFileIds() {
        return fileIds;
    }

    public void setFileIds(long[] fileIds) {
        this.fileIds = fileIds;
    }
}
