package com.cjh.common.shared.utils;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.*;

public class ZipUtils {

    private static final Logger logger = LoggerFactory.getLogger(ZipUtils.class);

    public static boolean compressFilesToZip(String folderPath, String zipFilePath) {
        Path sourcePath = Paths.get(folderPath);
        Path zipPath = Paths.get(zipFilePath);

        // 선행 작업 수행
        if (!beforeCompressFilesToZip(sourcePath, zipPath)) {
            return false;
        }

        try (OutputStream fos = Files.newOutputStream(zipPath);
             BufferedOutputStream bos = new BufferedOutputStream(fos);
             ZipArchiveOutputStream aos = new ZipArchiveOutputStream(bos)) {

            // 지정된 폴더 경로 내의 모든 파일을 탐색
            Files.walk(sourcePath).filter(path -> !Files.isDirectory(path)).forEach(path -> {
                // 각 파일에 대한 ZipArchiveEntry 생성
                ZipArchiveEntry zipEntry = new ZipArchiveEntry(sourcePath.relativize(path).toString());
                try (InputStream fis = Files.newInputStream(path);
                     BufferedInputStream bis = new BufferedInputStream(fis)) {

                    // ZipArchiveEntry를 ArchiveOutputStream에 추가
                    aos.putArchiveEntry(zipEntry);
                    byte[] buffer = new byte[1024];
                    int len;
                    while ((len = bis.read(buffer)) > 0) {
                        aos.write(buffer, 0, len);
                    }
                    aos.closeArchiveEntry();

                } catch (IOException e) {
                    logger.error("ZIP 파일 생성 오류 => " + path + " - " + e.getMessage());
                }
            });
            aos.finish();
            return true;

        } catch (IOException e) {
            logger.error("ZIP 파일 생성 오류 => " + e.getMessage());
            return false;
        }
    }

    private static boolean beforeCompressFilesToZip(Path sourcePath, Path zipPath) {
        Path zipDirPath = zipPath.getParent();

        try {
            // ZIP 파일 경로가 존재하지 않으면 생성
            if (Files.notExists(zipDirPath)) {
                Files.createDirectories(zipDirPath);
            }

            // 기존 ZIP 파일이 존재하면 삭제
            Files.deleteIfExists(zipPath);

            // 폴더에 파일이 없는 경우 false 반환
            if (Files.walk(sourcePath).filter(path -> !Files.isDirectory(path)).count() == 0) {
                logger.error("경로에 파일이 없습니다 => " + sourcePath.toString());
                return false;
            }

            return true;
        } catch (IOException e) {
            logger.error("ZIP 파일 생성 선행 작업 오류 => " + sourcePath.toString() + " - " + e.getMessage());
            return false;
        }
    }

    // public static void main(String[] args) {
    //     String folderPath = "d:\\test\\storage\\";
    //     String zipFileName = "d:\\test\\zip\\output.zip";

    //     boolean result = compressFilesToZip(folderPath, zipFileName);
    //     if (result) {
    //         logger.info("ZIP 파일이 성공적으로 생성되었습니다: " + zipFileName);
    //     } else {
    //         logger.error("ZIP 파일 생성에 실패하였습니다.");
    //     }
    // }
}
