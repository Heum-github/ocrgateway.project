package com.cjh.common.shared.utils;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.io.FilenameUtils;

public class PathUtils {

    // 경로를 연결하는 메소드
    public static Path combine(String first, String... more) {
        return Paths.get(first, more);
    }
    public static Path combine2(String first, String first2) {
        return Paths.get(first, first2);
    }
    // 파일명 추출 (확장자 포함)
    public static String getFileName(String path) {
        return Paths.get(path).getFileName().toString();
    }

    // 파일명만 추출 (확장자 제외)
    public static String getFileNameWithoutExtension(String path) {
        String fileName = getFileName(path);
        int dotIndex = fileName.lastIndexOf('.');
        return dotIndex == -1 ? fileName : fileName.substring(0, dotIndex);
    }

    // 확장자만 추출
    public static String getExtension(String path) {
        String fileName = getFileName(path);
        int dotIndex = fileName.lastIndexOf('.');
        return dotIndex == -1 ? "" : fileName.substring(dotIndex + 1);
    }

    // 경로 추출 (파일명 제외)
    public static Path getDirectoryPath(String path) {
        return Paths.get(path).getParent();
    }

    public static String extractFilename(String fullPath) {
        return FilenameUtils.getName(fullPath);
    }

    public static String extractExtension(String fullPath) {
        return FilenameUtils.getExtension(fullPath);
    }

    public static String extractBaseName(String fullPath) {
        return FilenameUtils.getBaseName(fullPath);
    }

    public static String extractFilePath(String fullPath) {
        return FilenameUtils.getFullPathNoEndSeparator(fullPath);
    }

    public static String concat(String basePath, String fileName) {
        return FilenameUtils.concat(basePath, fileName);
    }

    public static String concat(String... parts) {
        Path path = Paths.get(parts[0]);
        for (int i = 1; i < parts.length; i++) {
            path = path.resolve(parts[i]);
        }
        return path.toString();
    }

    public static List<String> getFilesByPattern(String directoryPath, String pattern) {
        Path dir = Paths.get(directoryPath);

        try (Stream<Path> stream = Files.list(dir)) {
            return stream.filter(file -> file.getFileName().toString().matches(pattern))
                         .map(Path::toString)
                         .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public static List<String> getFilePathsWithPattern(String directoryPath, String filePattern) {
        File dir = new File(directoryPath);

        if (!dir.exists() || !dir.isDirectory()) {
            return new ArrayList<>();
        }

        FilenameFilter filter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.startsWith(filePattern);
            }
        };

        File[] files = dir.listFiles(filter);
        List<String> filePaths = new ArrayList<>();
        if (files != null) {
            for (File file : files) {
                filePaths.add(file.getAbsolutePath());
            }
        }

        return filePaths;
    }

    public static boolean createDirectoryIfNotExists(String directoryPath) {
        try {
            Path path = Paths.get(directoryPath);
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
