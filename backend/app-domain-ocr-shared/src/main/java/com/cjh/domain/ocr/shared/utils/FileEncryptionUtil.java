package com.cjh.domain.ocr.shared.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class FileEncryptionUtil {

    private static final String ALGORITHM = "AES";
    private static final int TAG_BIT_LENGTH = 128;
    private static final int IV_SIZE = 12;

    public static void encryptFile(File inputFile, File outputFile, String key) throws Exception {

        // IV 생성
        byte[] iv = new byte[IV_SIZE];
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);

        // 암호화
        byte[] encryptedData = doCrypto(Cipher.ENCRYPT_MODE, key, iv, inputFile);

        try (FileOutputStream outputStream = new FileOutputStream(outputFile)) {
            outputStream.write(iv);
            outputStream.write(encryptedData);
        }
    }

    public static void decryptFile(File inputFile, File outputFile, String key) throws Exception {
        try (FileInputStream inputStream = new FileInputStream(inputFile)) {
            byte[] iv = new byte[IV_SIZE];
            if (inputStream.read(iv) != IV_SIZE) {
                throw new IllegalArgumentException("Invalid input file format");
            }

            // 나머지 파일 내용을 읽어서 복호화
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            byte[] data = new byte[1024];
            int nRead;
            while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }

            byte[] encryptedData = buffer.toByteArray();
            byte[] decryptedData = doCrypto(Cipher.DECRYPT_MODE, key, iv, encryptedData);

            try (FileOutputStream outputStream = new FileOutputStream(outputFile)) {
                outputStream.write(decryptedData);
            }
        }
    }

    private static byte[] doCrypto(int cipherMode, String key, byte[] iv, File inputFile) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(Base64.getDecoder().decode(key), ALGORITHM);
        GCMParameterSpec parameterSpec = new GCMParameterSpec(TAG_BIT_LENGTH, iv);

        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        cipher.init(cipherMode, secretKey, parameterSpec);

        try (FileInputStream inputStream = new FileInputStream(inputFile);
                ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {

            byte[] data = new byte[1024];
            int nRead;
            while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }

            byte[] inputBytes = buffer.toByteArray();
            return cipher.doFinal(inputBytes);

        }
    }

    private static byte[] doCrypto(int cipherMode, String key, byte[] iv, byte[] inputBytes) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(Base64.getDecoder().decode(key), ALGORITHM);
        GCMParameterSpec parameterSpec = new GCMParameterSpec(TAG_BIT_LENGTH, iv);

        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        cipher.init(cipherMode, secretKey, parameterSpec);

        return cipher.doFinal(inputBytes);
    }

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

    // public static void main(String[] args) {
    // try {
    // // 파일 경로 설정
    // File inputFile = new File("D:\\test\\storage\\sample.png");
    // File encryptedFile = new File("D:\\test\\storage\\sample-encrypted.enc");
    // File decryptedFile = new File("D:\\test\\storage\\sample-decrypted.png");

    // String key = "DUSW+92aOycv93YJSeY565X668kKYjTVeuLwOfiVPDE=";

    // // 파일 암호화
    // encryptFile(inputFile, encryptedFile, key);
    // System.out.println("파일 암호화 성공. 파일경로 => " + encryptedFile.getAbsolutePath());

    // // 파일 복호화
    // decryptFile(encryptedFile, decryptedFile, key);
    // System.out.println("파일 복호화 성공. 파일경로 => " + decryptedFile.getAbsolutePath());

    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    // }
}
