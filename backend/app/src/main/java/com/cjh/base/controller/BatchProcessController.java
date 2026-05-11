package com.cjh.base.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.cjh.domain.ocr.core.interfaces.FileParsingService;

import java.util.List;

@RestController
@RequestMapping("/batch")
public class BatchProcessController { // Contoller 오타 수정

    // Logger의 대상 클래스를 현재 클래스인 BatchProcessController로 변경
    private static final Logger logger = LoggerFactory.getLogger(BatchProcessController.class);

    private final FileParsingService parser;

    // 생성자를 통한 Service 의존성 주입 (Spring 4.3 이상에서는 @Autowired 생략 가능)
    public BatchProcessController(FileParsingService parser) {
        this.parser = parser;
    }

    @PostMapping("/upload-keys")
    public String uploadImageKeys(@RequestParam("file") MultipartFile file) {
        logger.info("배치 파일 업로드 요청 수신. 파일명: {}", file.getOriginalFilename());

        // 1. 파일 존재 여부 검증
        if (file.isEmpty()) {
            logger.warn("업로드된 파일이 비어 있습니다.");
            return "Fail: 파일이 비어있습니다.";
        }

        try {
            // 2. Service를 호출하여 파일 파싱 진행
            List<String> imageKeys = parser.parseImageKeyFile(file);
            logger.info("성공적으로 파싱된 이미지 키 개수: {} 건", imageKeys.size());

            // 3. TODO: 추출된 키들을 MariaDB에 적재하고 RabbitMQ로 메시지 전송
            // imageFileService.sendToQueue(imageKeys);

            // 처리 결과 반환
            return "Success: 파일 파싱 완료. 총 " + imageKeys.size() + "건의 이미지 키 처리가 대기 중입니다.";

        } catch (IllegalArgumentException e) {
            // 확장자 오류 등의 검증 실패 시
            logger.error("파일 유효성 검증 실패: {}", e.getMessage());
            return "Fail: " + e.getMessage();

        } catch (Exception e) {
            // 기타 서버 내부 오류 발생 시
            logger.error("파일 처리 중 시스템 오류 발생", e);
            return "Error: 파일 처리 중 문제가 발생했습니다.";
        }
    }
}