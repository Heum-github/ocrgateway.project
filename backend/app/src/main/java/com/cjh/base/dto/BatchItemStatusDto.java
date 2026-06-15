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
@Schema(description = "개별 항목 처리 상태 응답 객체")
public class BatchItemStatusDto {

    @Schema(description = "성공 여부", example = "true")
    private boolean success;

    @Schema(description = "데이터 영역")
    private Data data;

    public static BatchItemStatusDto success(Data data) {
        return new BatchItemStatusDto(true, data);
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Data {
        @Schema(description = "배치 ID", example = "b-12345-xyz")
        private String batchId;

        @Schema(description = "아이템 상세 상태 목록")
        private List<ItemStatus> items;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ItemStatus {
        @Schema(description = "이미지 키", example = "IMG_001")
        private String imageKey;

        @Schema(description = "처리 상태 (PENDING, PROCESSING, COMPLETED)", example = "COMPLETED")
        private String status;
    }
}
