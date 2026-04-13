package com.cjh.common.shared.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtils {
    private static final String DEFAULT_DATETIME_FORMAT = "yyyyMMddHHmmss";
    private static final String DEFAULT_DATE_FORMAT = "yyyyMMdd";

    private DateTimeUtils() {
    }

    public static String getCurrentDateTime() {
        return formatDateTime(new Date(), DEFAULT_DATETIME_FORMAT);
    }

    public static String getCurrentDateTime(String format) {
        return formatDateTime(new Date(), format);
    }

    public static String formatDateTime(Date date) {
        return formatDateTime(date, DEFAULT_DATETIME_FORMAT);
    }

    public static String formatDateTime(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    public static String getCurrentDate() {
        return formatDate(new Date(), DEFAULT_DATE_FORMAT);
    }

    public static String formatDate(Date date) {
        return formatDate(date, DEFAULT_DATE_FORMAT);
    }

    public static String formatDate(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }
}

