package com.cjh.domain.ocr.core.utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ValueConversionUtils {
    
    private ValueConversionUtils() {
        throw new UnsupportedOperationException("");
    }
    
    private static final DateTimeFormatter FMT_YYYYMMDD = DateTimeFormatter.ofPattern("yyyyMMdd");
    private static final DateTimeFormatter FMT_YYYYMMDD_HHMMSS = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    private static final DateTimeFormatter FMT_DASH_DATE = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter FMT_DASH_DATETIME = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // String to Long 변환
    public static long parseStringToLong(String s) {
        try {
            if (s == null || s.isBlank()) {
                return 0L; // null이나 빈값이면 0 반환
            }
            return Long.parseLong(s.trim());
        } catch (NumberFormatException e) {
            return 0L; // 형식이 잘못된 경우도 0 반환
        }
    }
    
    // Null Value 처리
    public static String nvl(String s) {
        return (s == null ? "" : s);
    }

    public static void validateNotEmpty(String value) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException("필수값 null 처리");
        }
    }    

    public static void validatePositive(Long value, String field) {
        if (value == null || value <= 0L) {
            throw new IllegalArgumentException(field + "는(은) 0보다 커야 합니다.");
        }
    }

    public static LocalDateTime toLocalDateTime(String raw) {
        if (raw == null) return null;
        String s = raw.trim();
        if (s.isEmpty()) return null;

        // epoch millis / seconds
        if (s.matches("^\\d{13}$")) {
            return Instant.ofEpochMilli(Long.parseLong(s)).atZone(ZoneId.systemDefault()).toLocalDateTime();
        }
        if (s.matches("^\\d{10}$")) {
            return Instant.ofEpochSecond(Long.parseLong(s)).atZone(ZoneId.systemDefault()).toLocalDateTime();
        }

        // 숫자
        if (s.matches("^\\d+$")) {
            if (s.length() == 8) { // yyyyMMdd
                return LocalDate.parse(s, FMT_YYYYMMDD).atStartOfDay();
            }
            if (s.length() == 14) { // yyyyMMddHHmmss
                return LocalDateTime.parse(s, FMT_YYYYMMDD_HHMMSS);
            }
        }

        // 하이픈
        if (s.length() == 10) { // yyyy-MM-dd
            return LocalDate.parse(s, FMT_DASH_DATE).atStartOfDay();
        }
        if (s.length() == 19) { // yyyy-MM-dd HH:mm:ss
            return LocalDateTime.parse(s, FMT_DASH_DATETIME);
        }

        try {
            return LocalDateTime.parse(s);
        } catch (Exception ignore) {}

        throw new DateTimeParseException("Unsupported datetime format", s, 0);
    }

    public static LocalDateTime toLocalDateTime(long epochMilli) {
        return Instant.ofEpochMilli(epochMilli)
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    // OCR처리유형 변환
    public static String convertOcrDaltypeCode(String daltypeCode) {
        if (daltypeCode == null) {
            return null;
        }
        switch (daltypeCode) {
            case "01":
                return "최초";
            case "02":
                return "재처리";
            case "03":
                return "전체처리";
            case "04":
                return "배치";
            case "05":
                return "보완";
            case "06":
                return "접수복사";
            case "07":
                return "성능테스트(임시)";
            case "99":
                return "테스트";
            default:
                return daltypeCode;
        }
    }
}
