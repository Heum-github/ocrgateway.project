package com.cjh.domain.ocr.core.constants;

import java.util.concurrent.atomic.AtomicBoolean;

public class OcrDomainConstants {
    // 데이터베이스
    public static class DB {
        public static AtomicBoolean DATABASE_CONNECT_HEALTH = new AtomicBoolean(true);
    }
}
