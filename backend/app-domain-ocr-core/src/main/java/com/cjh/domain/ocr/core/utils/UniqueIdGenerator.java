package com.cjh.domain.ocr.core.utils;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.security.SecureRandom;

public class UniqueIdGenerator {

    private static final String ALPHABET = "0123456789abcdefghijklmnopqrstuvwxyz";
    private static final SecureRandom RNG = new SecureRandom();
    private static final ZoneId KST = ZoneId.of("Asia/Seoul");

    public static String getServerName() {
        String raw = System.getenv("HOSTNAME");
        if (raw == null || raw.isEmpty()) raw = System.getenv("COMPUTERNAME");
        if (raw == null || raw.isEmpty()) raw = "server";
        String sanitized = raw.replaceAll("[^A-Za-z0-9]", "");
        if (sanitized.isEmpty()) sanitized = "server";
        return sanitized.length() > 12 ? sanitized.substring(0, 12) : sanitized;
    }

    public static String getServerName(String serverName) {
        String sanitized = serverName.replaceAll("[^A-Za-z0-9]", "");
        if (sanitized.isEmpty()) sanitized = "server";
        return sanitized.length() > 12 ? sanitized.substring(0, 12) : sanitized;
    }

    private static String randomBase62(int len) {
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append(ALPHABET.charAt(RNG.nextInt(ALPHABET.length())));
        }
        return sb.toString();
    }

    private static String nowStamp() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        return ZonedDateTime.now(KST).format(fmt);
    }

    public static String generateNewId() {
        String server = getServerName();
        String ts = nowStamp();
        String rand = randomBase62(10);
        return (ts + "-" + server + "-" + rand).toLowerCase();
    }

    public static void main(String[] args) {
        System.out.println(generateNewId());
    }
}