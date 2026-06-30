package com.cjh.domain.ocr.shared.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "기본 공통 응답 포맷 (Mocking 용도)")
public class BatchResponseDto {
    
    @Schema(description = "성공 여부", example = "true")
    private boolean success;
    
    @Schema(description = "데이터 영역")
    private Object data;
    
    public static BatchResponseDto success(Object data) {
        return new BatchResponseDto(true, data);
    }
    
    public static BatchResponseDto fail(String message) {
        return new BatchResponseDto(false, message);
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "배치 파일 업로드 응답 데이터")
    public static class UploadData {
        @Schema(description = "생성된 배치 ID", example = "b-12345-xyz")
        private String batchId;
        
        @Schema(description = "파싱된 전체 아이템 수", example = "5000")
        private int totalItemCount;
        
        @Schema(description = "처리 메시지", example = "파일 파싱 완료. 작업 대기열에 등록되었습니다.")
        private String message;
    }
}
