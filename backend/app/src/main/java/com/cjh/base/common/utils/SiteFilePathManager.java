package com.cjh.claim.common.utils;

import java.nio.file.Path;
import java.util.UUID;

import com.cjh.claim.common.constants.SiteConstants;
import com.cjh.common.shared.constants.CommonSiteConstants;
import com.cjh.common.shared.utils.DateTimeUtils;
import com.cjh.common.shared.utils.PathUtils;
import com.cjh.domain.ocr.shared.constants.OcrConstants;


public class SiteFilePathManager {

    // 다운로드 이미지 파일 저장 경로
    public static String getDownloadImagePath(String scheduleId, String fileName) {

        if(scheduleId == null)
            return PathUtils.concat(SiteConstants.LOCAL_FILE_STORAGE.BASE_PATH, DateTimeUtils.getCurrentDate(), generateRandomString(32), fileName);

        String basePath = getDownloadImageBasePath(scheduleId);

        return PathUtils.concat(basePath, fileName);
    }
    
    // 다운로드 이미지 파일 저장 기본 경로
    public static String getDownloadImageBasePath(String scheduleId) {

        Path dirPath = PathUtils.combine(SiteConstants.LOCAL_FILE_STORAGE.BASE_PATH, DateTimeUtils.getCurrentDate(), scheduleId, generateRandomString(32));
        return dirPath.toString();
    }
    
    // 분할 이미지 파일 저장 경로
    public static String getSplitImagePath(String orgFilePath) {
        // return PathUtils.concat(PathUtils.extractFilePath(orgFilePath), "images");
        return PathUtils.concat(PathUtils.extractFilePath(orgFilePath));
    }

    // 회전 이미지 파일명
    public static String getRotateImageName(String fileName, int rotate) {

        String filePath = PathUtils.concat(SiteConstants.LOCAL_FILE_STORAGE.BASE_PATH, DateTimeUtils.getCurrentDate(), PathUtils.extractBaseName(fileName) + "_rotate_" + rotate + "." + PathUtils.extractExtension(fileName));
        return filePath;
    }

    // 마스킹 이미지 파일명
    public static String getMaskingImageName(String orgFilePath) {
        String filePath = PathUtils.concat(PathUtils.extractFilePath(orgFilePath), PathUtils.extractBaseName(orgFilePath) + "_masking" + "." + PathUtils.extractExtension(orgFilePath));
        return filePath;
    }

    // PDF 변환 파일명
    public static String getConvertPdfName(String fileName, int rotate) {

        String filePath = PathUtils.concat(SiteConstants.LOCAL_FILE_STORAGE.BASE_PATH, DateTimeUtils.getCurrentDate(), PathUtils.extractBaseName(fileName) + "." + CommonSiteConstants.EXTENSIONS.PDF);
        return filePath;
    }

    // PNG 변환 파일명
    public static String getConvertPngName(String fileName, int rotate) {

        String filePath = PathUtils.concat(SiteConstants.LOCAL_FILE_STORAGE.BASE_PATH, DateTimeUtils.getCurrentDate(), PathUtils.extractBaseName(fileName) + "." + OcrConstants.SPLIT_FILE_EXTENSION);
        return filePath;
    }

    private static String generateRandomString(int length) {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        return uuid.substring(0, length);
    }
}

