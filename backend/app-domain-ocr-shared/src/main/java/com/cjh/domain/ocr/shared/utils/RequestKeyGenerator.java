package com.cjh.domain.ocr.shared.utils;

import java.util.UUID;

import com.cjh.common.shared.constants.CommonSiteConstants;

public class RequestKeyGenerator {

    public static String generateKey() {
        return generateKey("");
    }

    public static String generateKey(String code) {

        String ip = "";
        if (CommonSiteConstants.ENV.IP != "") {
            String ipAddress = CommonSiteConstants.ENV.IP.replaceAll("\\.", "");
            ip = ipAddress.length() > 3 ? ipAddress.substring(ipAddress.length() - 3) : ipAddress;
        }

        String uuid = UUID.randomUUID().toString().substring(0, 8);

        // 최종 키 생성
        return code + String.valueOf(System.currentTimeMillis()) + uuid + ip;
    }
    // public static void main(String[] args) {
    // String code = "";
    // System.out.println("생성된 키 : " + generateKey(code));
    // }
}
