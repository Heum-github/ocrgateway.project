package com.cjh.common.shared.utils;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Base64;

public class AESKeyGenerator {

    public static void main(String[] args) {
        try {
            // AES 알고리즘으로 키 생성기 초기화 (256비트)
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(256); // 256비트 키 생성

            // 키 생성
            SecretKey secretKey = keyGen.generateKey();

            // Base64로 인코딩된 키 출력
            String encodedKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());
            // System.out.println("Generated AES-256 Key: " + encodedKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
