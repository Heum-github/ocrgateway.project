package com.cjh.domain.ocr.core.interfaces;

import java.util.Optional;

import com.cjh.common.shared.utils.CommonApiResponse;

public interface UpstageOcrApiService {
    public CommonApiResponse ocr(String filePath, String model, boolean verbose);
    public CommonApiResponse saveOcrResultAsJson(String filePath, String model, boolean verbose);
    public CommonApiResponse ocr(String filePath, Optional<String> docType);
    public CommonApiResponse healthCheck();
}
