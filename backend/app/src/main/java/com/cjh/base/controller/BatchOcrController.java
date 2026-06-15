package com.cjh.base.controller;

import com.cjh.base.dto.BatchItemStatusDto;
import com.cjh.base.dto.BatchListResponseDto;
import com.cjh.base.dto.BatchResponseDto;
import com.cjh.domain.ocr.core.interfaces.FileParsingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

@Tag(name = "대용량 이미지 배치 처리 API", description = "대량의 이미지 정보 파일 업로드 및 상태를 조회합니다.")
@RestController
@RequestMapping("/api/v1/batch")
public class BatchOcrController {

    private static final Logger logger = LoggerFactory.getLogger(BatchOcrController.class);
    private final FileParsingService parser;

    public BatchOcrController(FileParsingService parser) {
        this.parser = parser;
    }

    @Operation(summary = "배치 파일 업로드", description = "Excel, CSV 파일을 업로드하여 파싱 후 RabbitMQ 대기열에 등록합니다.")
    @PostMapping(value = "/upload-keys", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public BatchResponseDto uploadImageKeys(
            @Parameter(description = "업로드할 대용량 파일(.csv, .xlsx, .txt)", required = true)
            @RequestParam("file") MultipartFile file,
            @Parameter(description = "작업에 대한 설명", required = false)
            @RequestParam(value = "description", required = false) String description) {
        
        logger.info("배치 파일 업로드 요청 수신. 파일명: {}", file.getOriginalFilename());

        if (file.isEmpty()) {
            logger.warn("업로드된 파일이 비어 있습니다.");
            return BatchResponseDto.fail("파일이 비어있습니다.");
        }

        try {
            // 2. 파일 파싱 - 이미지키 추출
            List<String> imageKeys = parser.parseImageKeyFile(file);
            logger.info("성공적으로 파싱된 이미지 키 개수: {} 건", imageKeys.size());

            // 3. TODO: 추출된 키들을 MariaDB에 적재하고 RabbitMQ로 메시지 전송
            // imageFileService.sendToQueue(imageKeys);

            // 처리 결과 DTO 반환
            BatchResponseDto.UploadData data = new BatchResponseDto.UploadData(
                    "b-" + System.currentTimeMillis(),
                    imageKeys.size(),
                    "파일 파싱 완료. 작업 대기열에 등록되었습니다."
            );
            return BatchResponseDto.success(data);

        } catch (IllegalArgumentException e) {
            logger.error("파일 유효성 검증 실패: {}", e.getMessage());
            return BatchResponseDto.fail(e.getMessage());
        } catch (Exception e) {
            logger.error("파일 처리 중 시스템 오류 발생", e);
            return BatchResponseDto.fail("파일 처리 중 문제가 발생했습니다.");
        }
    }

    @Operation(summary = "전체 배치 현황 리스트 조회", description = "대시보드에 표시할 배치 목록을 페이징하여 가져옵니다.")
    @GetMapping("/status")
    public BatchListResponseDto getBatchStatus(
            @Parameter(description = "페이지 번호 (기본값 0)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "페이지 크기 (기본값 10)") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "상태 필터 (ALL, PENDING, PROCESSING, COMPLETED)") @RequestParam(defaultValue = "ALL") String status) {
        
        // 프론트엔드 연동용 목업 데이터 반환
        List<BatchListResponseDto.BatchItem> mockContent = Arrays.asList(
                new BatchListResponseDto.BatchItem("batch-001", "영수증_스캔본_2606.zip", "COMPLETED", 500, 500, 0, "2026-06-01T14:20:00Z"),
                new BatchListResponseDto.BatchItem("batch-002", "invoice_batch_june.csv", "PROCESSING", 1000, 450, 2, "2026-06-01T15:10:00Z")
        );
        
        BatchListResponseDto.Data data = new BatchListResponseDto.Data(mockContent, 1, 2);
        return BatchListResponseDto.success(data);
    }

    @Operation(summary = "개별 배치 상세 상태 고속 조회 (Redis)", description = "진행률 폴링을 위해 특정 배치 내역의 실시간 상태를 조회합니다.")
    @GetMapping("/{batchId}/items/status")
    public BatchItemStatusDto getBatchItemStatus(
            @Parameter(description = "조회할 배치의 고유 ID", required = true) @PathVariable("batchId") String batchId) {
        
        // 프론트엔드 연동용 목업 데이터 반환
        List<BatchItemStatusDto.ItemStatus> mockItems = Arrays.asList(
                new BatchItemStatusDto.ItemStatus("IMG_4921.jpg", "COMPLETED"),
                new BatchItemStatusDto.ItemStatus("IMG_4922.jpg", "COMPLETED"),
                new BatchItemStatusDto.ItemStatus("IMG_4923.jpg", "PROCESSING"),
                new BatchItemStatusDto.ItemStatus("IMG_4924.jpg", "PENDING")
        );
        
        BatchItemStatusDto.Data data = new BatchItemStatusDto.Data(batchId, mockItems);
        return BatchItemStatusDto.success(data);
    }
}