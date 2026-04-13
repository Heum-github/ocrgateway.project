package com.cjh.domain.ocr.core.interfaces;

import java.util.List;

public interface DocumentSplitService {
    public List<String> split(String filePath, String outputPath);
    public List<String> splitPdfToSinglePdfPage(String filePath, String outputPath);
    public int pageCount(String filePath);
}
