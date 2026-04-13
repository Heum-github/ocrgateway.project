package com.cjh.common.shared.utils;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.jasypt.iv.RandomIvGenerator;

public class JasyptEncryptionTest {

    public static void main(String[] args) {
        StringEncryptor encryptor = getEncryptor();
        String originalText = "qhdkscjfwj1!";
        
        String encryptedText = encryptor.encrypt(originalText);
        // System.out.println("Encrypted Text: " + encryptedText);
        
        String decryptedText = encryptor.decrypt(encryptedText);
        // System.out.println("Decrypted Text: " + decryptedText);

        StringEncryptor encryptor2 = getPasswordEncryptor();

        String originalText2 = "qhdkscjfwj1!";
        String encryptedText2 = encryptor2.encrypt(originalText2);
        // System.out.println("Encrypted Text2: " + encryptedText2);
        
        String decryptedText2 = encryptor2.decrypt(encryptedText);
        // System.out.println("Decrypted Text2: " + decryptedText2);
        
        assert originalText.equals(decryptedText);
    }

    private static StringEncryptor getEncryptor() {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        
        config.setPassword("password!@");
        config.setAlgorithm("PBEWithMD5AndDES");
        config.setKeyObtentionIterations("1000");
        config.setPoolSize("1");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setStringOutputType("base64");
        encryptor.setConfig(config);
        
        return encryptor;
    }

    public static StringEncryptor getPasswordEncryptor() {
        
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        
        config.setPassword("password!@");
        config.setAlgorithm("PBEWithHMACSHA512AndAES_256");
        config.setKeyObtentionIterations("1000");
        config.setPoolSize("1");
        config.setIvGenerator(new RandomIvGenerator());
        //config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setStringOutputType("base64");

        encryptor.setConfig(config);
        
        return encryptor;
    }
}