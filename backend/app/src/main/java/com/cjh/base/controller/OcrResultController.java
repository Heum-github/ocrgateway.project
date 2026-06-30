package com.cjh.base.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import com.cjh.domain.ocr.shared.dto.OcrResultDto;

import java.util.Arrays;
import java.util.List;

@Tag(name = "OCR 결과 검증 API", description = "LayoutLM 분석 결과 및 바운딩 박스 정보를 제공합니다.")
@RestController
@RequestMapping("/api/v1/ocr")
public class OcrResultController {

    @Operation(summary = "OCR 상세 결과 조회", description = "이미지 원본 URL과 추출된 텍스트, '절대 좌표' 기반의 바운딩 박스를 반환합니다.")
    @GetMapping("/result/{imageKey}")
    public OcrResultDto getOcrResult(
            @Parameter(description = "결과를 확인할 이미지의 고유 키 (예: IMG_001)", required = true)
            @PathVariable("imageKey") String imageKey) {

        // 프론트엔드 연동용 목업 데이터 반환 (추후 DB 연결 시 변경)
        List<OcrResultDto.LayoutBox> mockLayout = Arrays.asList(
                new OcrResultDto.LayoutBox("box-1", "INVOICE NUMBER: 00123", 0.99, new int[]{50, 100, 350, 150}),
                new OcrResultDto.LayoutBox("box-2", "TOTAL AMOUNT: $4,500.00", 0.88, new int[]{50, 200, 400, 250}),
                new OcrResultDto.LayoutBox("box-3", "DATE: 2026-06-01", 0.95, new int[]{500, 100, 750, 150})
        );

        OcrResultDto.Data data = new OcrResultDto.Data(
                imageKey,
                "https://images.unsplash.com/photo-1559811814-e2c56a5c192b?q=80&w=800&auto=format&fit=crop",
                800,
                1000,
                mockLayout
        );

        return OcrResultDto.success(data);
    }
}
