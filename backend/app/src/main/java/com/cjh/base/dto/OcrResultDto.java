package com.cjh.base.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "OCR 상세 결과 응답 객체")
public class OcrResultDto {

    @Schema(description = "성공 여부", example = "true")
    private boolean success;

    @Schema(description = "데이터 영역")
    private Data data;

    public static OcrResultDto success(Data data) {
        return new OcrResultDto(true, data);
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Data {
        @Schema(description = "결과를 확인할 이미지의 고유 키", example = "IMG_001")
        private String imageKey;

        @Schema(description = "원본 이미지의 MinIO URL", example = "https://images.unsplash.com/photo-1559811814-e2c56a5c192b?q=80&w=800&auto=format&fit=crop")
        private String imageUrl;

        @Schema(description = "원본 이미지 전체 가로 폭 (픽셀 단위)", example = "800")
        private int imageWidth;

        @Schema(description = "원본 이미지 전체 세로 높이 (픽셀 단위)", example = "1000")
        private int imageHeight;

        @Schema(description = "추출된 레이아웃 데이터 목록")
        private List<LayoutBox> layoutData;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "바운딩 박스 상세 정보")
    public static class LayoutBox {
        @Schema(description = "박스 고유 ID", example = "box-1")
        private String id;

        @Schema(description = "추출된 텍스트 내용", example = "Invoice Number: 9981")
        private String text;

        @Schema(description = "신뢰도 (0~1 사이)", example = "0.98")
        private double confidence;

        @Schema(description = "절대 픽셀 좌표 [x_min, y_min, x_max, y_max]", example = "[50, 100, 350, 150]")
        private int[] boundingBox;
    }
}
