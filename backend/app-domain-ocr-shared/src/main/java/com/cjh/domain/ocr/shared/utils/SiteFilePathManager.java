package com.cjh.domain.ocr.shared.utils;

import java.io.File;
import java.util.UUID;

import com.cjh.common.shared.constants.CommonSiteConstants;
import com.cjh.common.shared.utils.DateTimeUtils;
import com.cjh.common.shared.utils.PathUtils;
import com.cjh.domain.ocr.shared.constants.OcrConstants;

public class SiteFilePathManager {

    // 분할 이미지 파일 저장 경로
    public static String getSplitImagePath(String fileName) {

        // String filePath =
        // PathUtils.concat(SiteConstants.LOCAL_FILE_STORAGE.BASE_PATH,
        // DateTimeUtil.getCurrentDate(), PathUtils.extractFilename(fileName) +
        // generateRandomString(8));
        // String filePath =
        // PathUtils.concat(SiteConstants.LOCAL_FILE_STORAGE.BASE_PATH,
        // DateTimeUtil.getCurrentDate(), PathUtils.extractBaseName(fileName));
        String filePath = PathUtils.concat(OcrConstants.LOCAL_FILE_STORAGE.BASE_PATH, DateTimeUtils.getCurrentDate())
                + File.separator;
        return filePath;
    }

    // 회전 이미지 파일명
    public static String getRotateImageName(String fileName, int rotate) {

        String filePath = PathUtils.concat(OcrConstants.LOCAL_FILE_STORAGE.BASE_PATH, DateTimeUtils.getCurrentDate(),
                PathUtils.extractBaseName(fileName) + "_rotate_" + rotate + "." + PathUtils.extractExtension(fileName));
        return filePath;
    }

    // 마스킹 이미지 파일명
    public static String getMaskingImageName(String fileName) {

        String filePath = PathUtils.concat(OcrConstants.LOCAL_FILE_STORAGE.BASE_PATH, DateTimeUtils.getCurrentDate(),
                PathUtils.extractBaseName(fileName) + "_masking" + "." + PathUtils.extractExtension(fileName));
        return filePath;
    }

    // PDF 변환 파일명
    public static String getConvertPdfName(String fileName, int rotate) {

        String filePath = PathUtils.concat(OcrConstants.LOCAL_FILE_STORAGE.BASE_PATH, DateTimeUtils.getCurrentDate(),
                PathUtils.extractBaseName(fileName) + "." + CommonSiteConstants.EXTENSIONS.PDF);
        return filePath;
    }

    // PNG 변환 파일명
    public static String getConvertPngName(String fileName, int rotate) {

        String filePath = PathUtils.concat(OcrConstants.LOCAL_FILE_STORAGE.BASE_PATH, DateTimeUtils.getCurrentDate(),
                PathUtils.extractBaseName(fileName) + "." + OcrConstants.SPLIT_FILE_EXTENSION);
        return filePath;
    }

    private static String generateRandomString(int length) {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        return uuid.substring(0, length);
    }
}
