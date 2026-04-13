package com.cjh.domain.ocr.core.services;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import org.apache.tika.Tika;
import org.springframework.stereotype.Service;

import com.cjh.domain.ocr.core.interfaces.FileDetermineService;

@Service
public class FileDetermineServiceImpl implements FileDetermineService {

    // 확장자 임시교체
    public String replaceExtension(String fullPath, String newExt) {
        int lastDot = fullPath.lastIndexOf('.');
        int lastSlash = Math.max(
                fullPath.lastIndexOf('/'),
                fullPath.lastIndexOf('\\')
        );

        // 확장자가 있는 경우
        if (lastDot > lastSlash) {
            return fullPath.substring(0, lastDot) + "." + newExt;
        }

        // 확장자가 없는 경우
        return fullPath + "." + newExt;
    }

    // 확장자 찾기
    public String detectExtension(String filePath) {

        if (filePath == null || filePath.isBlank()) {
            return "";
        }

        Path path = Path.of(filePath);

        if (!Files.exists(path) || !Files.isRegularFile(path)) {
            return "";
        }

        try {
            // Magic Number
            String ext = detectByMagicNumber(path);
            if (!"".equals(ext)) {
                return ext;
            }

            // Apache Tika fallback
            Tika tika = new Tika();
            String mime = tika.detect(path);
            return toExtension(mime);

        } catch (Exception e) {
            return "";
        }
    }

    public void renameFile(String from, String to) throws IOException {
        Path source = Path.of(from);
        Path target = Path.of(to);

        if (!Files.exists(source)) {
            throw new IllegalStateException("원본 파일이 존재하지 않음: " + from);
        }

        Files.move(
            source,
            target,
            StandardCopyOption.REPLACE_EXISTING, // 동일 이름 존재 시 덮어쓰기
            StandardCopyOption.ATOMIC_MOVE       // 가능하면 원자적 이동
        );
    }

    private String toExtension(String mime) {
        if (mime == null || mime.isBlank()) {
            return "";
        }

        return switch (mime) {

            // PDF
            case "application/pdf" -> "pdf";

            // JPEG
            case "image/jpeg" -> "jpg";

            // PNG
            case "image/png" -> "png";

            // TIFF (단일 / 멀티페이지)
            case "image/tiff" -> "tiff";

            // BMP
            case "image/bmp",
                "image/x-bmp" -> "bmp";

            // GIF
            case "image/gif" -> "gif";

            default -> "";
        };
    }


    public String detectByMagicNumber(Path path) throws IOException {
        try (InputStream is = Files.newInputStream(path)) {

            byte[] header = is.readNBytes(8);
            if (header.length < 4) {
                return "";
            }

            // PDF: %PDF
            if (header[0] == 0x25 && header[1] == 0x50
                    && header[2] == 0x44 && header[3] == 0x46) {
                return "pdf";
            }

            // JPG / JPEG: FF D8 FF
            if ((header[0] & 0xFF) == 0xFF
                    && (header[1] & 0xFF) == 0xD8
                    && (header[2] & 0xFF) == 0xFF) {
                return "jpg";
            }

            // PNG: 89 50 4E 47 0D 0A 1A 0A
            if ((header[0] & 0xFF) == 0x89
                    && header[1] == 0x50
                    && header[2] == 0x4E
                    && header[3] == 0x47
                    && header[4] == 0x0D
                    && header[5] == 0x0A
                    && header[6] == 0x1A
                    && header[7] == 0x0A) {
                return "png";
            }

            // GIF: GIF87a / GIF89a
            if (header[0] == 0x47 && header[1] == 0x49 && header[2] == 0x46
                    && header[3] == 0x38
                    && (header[4] == 0x37 || header[4] == 0x39)
                    && header[5] == 0x61) {
                return "gif";
            }

            // BMP: BM
            if (header[0] == 0x42 && header[1] == 0x4D) {
                return "bmp";
            }

            // TIFF (Little Endian): II 2A 00
            if (header[0] == 0x49 && header[1] == 0x49
                    && header[2] == 0x2A && header[3] == 0x00) {
                return "tiff";
            }

            // TIFF (Big Endian): MM 00 2A
            if (header[0] == 0x4D && header[1] == 0x4D
                    && header[2] == 0x00 && header[3] == 0x2A) {
                return "tiff";
            }

            return "";
        }
    }
}
