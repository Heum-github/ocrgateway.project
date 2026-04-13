package com.cjh.common.shared.utils;

public class CommonApiResponse {

    private String body;
    private int statusCode;
    private String message;

    public CommonApiResponse(int statusCode) {
        this.statusCode = statusCode;
    }

    public CommonApiResponse(String body, int statusCode) {
        this.body = body;
        this.statusCode = statusCode;
    }

    public CommonApiResponse(String body, int statusCode, String message) {
        this.body = body;
        this.statusCode = statusCode;
        this.message = message;
    }

    public String getBody() {
        return body;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }

    public boolean isStatusOk() {
        return statusCode >= 200 && statusCode < 300;
    }

    public static CommonApiResponse success(String body) {
        return new CommonApiResponse(body, 200, "OK");
    }
    
    public static CommonApiResponse error(int statusCode, String message) {
        return new CommonApiResponse(null, statusCode, message);
    }
}
