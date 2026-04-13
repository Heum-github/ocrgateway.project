package com.cjh.common.shared.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;

public class MultipartFileUtils {

    /**
     * 파일 경로를 MultipartFile 객체로 변환합니다.
     *
     * @param filePath 변환할 파일 경로
     * @return 변환된 MultipartFile 객체
     * @throws IllegalArgumentException 파일이 존재하지 않거나 접근할 수 없을 경우
     */
    public static MultipartFile fromFilePath(String filePath) {
        return new FilePathMultipartFile(filePath);
    }

    /**
     * MultipartFile 구현 클래스
     * 파일 경로를 기반으로 MultipartFile 동작을 수행합니다.
     */
    private static class FilePathMultipartFile implements MultipartFile {
        private final File file;

        public FilePathMultipartFile(String filePath) {
            this.file = new File(filePath);
            if (!file.exists()) {
                throw new IllegalArgumentException("파일이 존재하지 않습니다: " + filePath);
            }
        }

        @Override
        public String getName() {
            return file.getName();
        }

        @Override
        public String getOriginalFilename() {
            return file.getName();
        }

        @Override
        public String getContentType() {
            // MIME 타입을 기본값으로 설정하거나 파일 확장자를 기반으로 반환
            return "application/octet-stream";
        }

        @Override
        public boolean isEmpty() {
            return file.length() == 0;
        }

        @Override
        public long getSize() {
            return file.length();
        }

        @Override
        public byte[] getBytes() throws IOException {
            try (FileInputStream fis = new FileInputStream(file)) {
                return fis.readAllBytes();
            }
        }

        @Override
        public InputStream getInputStream() throws IOException {
            return new FileInputStream(file);
        }

        @Override
        public void transferTo(File dest) throws IOException, IllegalStateException {
            try (InputStream is = new FileInputStream(file);
                 OutputStream os = new FileOutputStream(dest)) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = is.read(buffer)) != -1) {
                    os.write(buffer, 0, bytesRead);
                }
            }
        }
    }

    // public static void main(String[] args) {
    //     // 테스트 파일 경로
    //     String testFilePath = "D:/sample_pages_5.tiff";

    //     try {
    //         // 파일 경로를 MultipartFile로 변환
    //         MultipartFile multipartFile = fromFilePath(testFilePath);

    //         System.out.println("파일 이름: " + multipartFile.getOriginalFilename());
    //         System.out.println("파일 크기: " + multipartFile.getSize());
    //         System.out.println("파일 MIME 타입: " + multipartFile.getContentType());

    //         // 파일 데이터 출력
    //         byte[] fileBytes = multipartFile.getBytes();
    //         System.out.println("파일 내용: " + new String(fileBytes));

    //         // 파일 복사 테스트
    //         File destinationFile = new File("D:/copy_testfile.tiff");
    //         multipartFile.transferTo(destinationFile);
    //         System.out.println("파일 복사가 완료되었습니다: " + destinationFile.getAbsolutePath());
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //     }
    // }
}
