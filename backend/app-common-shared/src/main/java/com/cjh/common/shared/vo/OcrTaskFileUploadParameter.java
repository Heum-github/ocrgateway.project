package com.cjh.common.shared.vo;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class OcrTaskFileUploadParameter {
    
    private String fileName;
    private String filePath;
    private String requestId;
    private String token;
    private String docType;
    private int rotate;
    private MultipartFile file;

    private List<Integer> x;
    private List<Integer> y;
    private List<Integer> width;
    private List<Integer> height;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
    
    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public int getRotate() {
        return rotate;
    }

    public void setRotate(int rotate) {
        this.rotate = rotate;
    }

    public List<Integer> getX() {
        return x;
    }

    public void setX(List<Integer> x) {
        this.x = x;
    }

    public List<Integer> getY() {
        return y;
    }

    public void setY(List<Integer> y) {
        this.y = y;
    }

    public List<Integer> getWidth() {
        return width;
    }

    public void setWidth(List<Integer> width) {
        this.width = width;
    }

    public List<Integer> getHeight() {
        return height;
    }

    public void setHeight(List<Integer> height) {
        this.height = height;
    }
}
