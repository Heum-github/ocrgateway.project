package com.cjh.common.core.interfaces;

import org.springframework.web.multipart.MultipartFile;

import com.cjh.common.shared.vo.FileStorageResult;

public interface FileStorageService {
    public FileStorageResult storeFile(MultipartFile file, String path, boolean useRandomFileName);
}
