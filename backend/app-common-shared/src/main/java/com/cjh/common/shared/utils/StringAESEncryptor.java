
package com.cjh.common.shared.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import java.security.SecureRandom;
import java.util.Base64;

public class StringAESEncryptor {

    private static final String ALGORITHM = "AES";
    private static final int TAG_BIT_LENGTH = 128;
    private static final int IV_SIZE = 12;

    public static String encrypt(String password, String key) throws Exception {
        byte[] iv = new byte[IV_SIZE];
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);

        byte[] encryptedPassword = doCrypto(Cipher.ENCRYPT_MODE, key, iv, password.getBytes());

        // IV와 암호화된 데이터를 합쳐서 Base64로 인코딩
        byte[] encryptedIvAndPassword = new byte[IV_SIZE + encryptedPassword.length];
        System.arraycopy(iv, 0, encryptedIvAndPassword, 0, IV_SIZE);
        System.arraycopy(encryptedPassword, 0, encryptedIvAndPassword, IV_SIZE, encryptedPassword.length);

        return Base64.getEncoder().encodeToString(encryptedIvAndPassword);
    }

    public static String decrypt(String encryptedPassword, String key) throws Exception {
        byte[] decodedData = Base64.getDecoder().decode(encryptedPassword);

        byte[] iv = new byte[IV_SIZE];
        System.arraycopy(decodedData, 0, iv, 0, IV_SIZE);

        byte[] encryptedBytes = new byte[decodedData.length - IV_SIZE];
        System.arraycopy(decodedData, IV_SIZE, encryptedBytes, 0, encryptedBytes.length);

        byte[] decryptedPassword = doCrypto(Cipher.DECRYPT_MODE, key, iv, encryptedBytes);

        return new String(decryptedPassword);
    }

    private static byte[] doCrypto(int cipherMode, String key, byte[] iv, byte[] inputBytes) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(Base64.getDecoder().decode(key), ALGORITHM);
        GCMParameterSpec parameterSpec = new GCMParameterSpec(TAG_BIT_LENGTH, iv);

        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        cipher.init(cipherMode, secretKey, parameterSpec);

        return cipher.doFinal(inputBytes);
    }

    public static void main(String[] args) throws Exception {

        var orgText = "qhdkscjfwj1!";
        var encText = encrypt(orgText, "DUSW+92aOycv93YJSeY565X668kKYjTVeuLwOfiVPDE=");
        // System.out.println(encText);
        var decText = decrypt(encText, "DUSW+92aOycv93YJSeY565X668kKYjTVeuLwOfiVPDE=");
        // System.out.println(decText);
    }
}
