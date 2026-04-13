package com.cjh.domain.ocr.shared.utils;

public class TheadLocalContext {
    private static final ThreadLocal<String> context = new ThreadLocal<>();

    public static void set(String largeClassCode, String inquiryTypeCode) {
        context.set(resolve(largeClassCode, inquiryTypeCode));
    }

    public static String get() {
        return context.get();
    }

    public static void clear() {
        context.remove();
    }

    private static String resolve(String largeClassCode, String inquiryTypeCode) {
        if ("CL".equals(largeClassCode)) return "P";
        if ("AM".equals(largeClassCode)) return "O";
        if ("NB".equals(largeClassCode)) {
            if ("01".equals(inquiryTypeCode)) return "B";
            if ("00".equals(inquiryTypeCode)) return "U";
        }
        throw new IllegalArgumentException(
            "Unknown code: largeClassCode=" + largeClassCode + ", inquiryTypeCode=" + inquiryTypeCode
        );
    }
}
