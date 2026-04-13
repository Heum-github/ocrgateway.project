package com.cjh.domain.ocr.shared.utils;

import java.io.File;

public class FileEncryptionPathUtil {

    public static String getEncryptedFilePath(File file) {
        if (file == null || !file.exists()) {
            throw new IllegalArgumentException("파일이 존재하지 않거나 null입니다.");
        }

        String directory = file.getParent();
        String fileName = file.getName();
        int dotIndex = fileName.lastIndexOf(".");
        String baseName = (dotIndex == -1) ? fileName : fileName.substring(0, dotIndex);
        String extension = (dotIndex == -1) ? "" : fileName.substring(dotIndex);

        // "encrypted" 문자열 파일명에 추가
        String encryptedFileName = baseName + "-encrypted" + extension;

        return directory + File.separator + encryptedFileName;
    }

    public static String getDecryptedFilePath(File file) {

        if (file == null || !file.exists()) {
            throw new IllegalArgumentException("파일이 존재하지 않거나 null입니다.");
        }

        String directory = file.getParent();
        String fileName = file.getName();
        int dotIndex = fileName.lastIndexOf(".");
        String baseName = (dotIndex == -1) ? fileName : fileName.substring(0, dotIndex);
        String extension = (dotIndex == -1) ? "" : fileName.substring(dotIndex);

        // "encrypted" 문자열 파일명에 추가
        String encryptedFileName = baseName + "-decrypted" + extension;

        return directory + File.separator + encryptedFileName;
    }
}
