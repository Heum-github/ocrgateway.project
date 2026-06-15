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
@Schema(description = "현황 리스트 응답 포맷")
public class BatchListResponseDto {

    @Schema(description = "성공 여부", example = "true")
    private boolean success;

    @Schema(description = "데이터 영역")
    private Data data;

    public static BatchListResponseDto success(Data data) {
        return new BatchListResponseDto(true, data);
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Data {
        @Schema(description = "페이지 목록 내용")
        private List<BatchItem> content;

        @Schema(description = "전체 페이지 수", example = "5")
        private int totalPages;

        @Schema(description = "전체 요소 수", example = "48")
        private int totalElements;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BatchItem {
        @Schema(description = "배치 ID", example = "b-12345-xyz")
        private String batchId;

        @Schema(description = "파일 이름", example = "batch_data_20260601.csv")
        private String fileName;

        @Schema(description = "상태 (PENDING, PROCESSING, COMPLETED)", example = "PROCESSING")
        private String status;

        @Schema(description = "전체 아이템 건수", example = "5000")
        private int totalCount;

        @Schema(description = "처리 완료된 건수", example = "2350")
        private int completedCount;

        @Schema(description = "오류 건수", example = "5")
        private int errorCount;

        @Schema(description = "등록 일시", example = "2026-06-01T10:00:00Z")
        private String createdAt;
    }
}
